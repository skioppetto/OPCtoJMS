package ac.opctojms;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.da.OPCSERVERSTATE;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Created by skioppetto on 25/03/14.
 */
public class ServerManager  {

    private final Server server;
    private final Map<String, Map<String, Item>> itemsCache = new HashMap<String, Map<String, Item>>();

    public ServerManager(IOPCConnectionInfo ci){
        ConnectionInformation conn = ConnectionInformationFactory.build(ci);
        server = new Server(conn, Executors.newSingleThreadScheduledExecutor());
    }

    /**try to connect to the OPC server.*/
    public void connect () throws OPCException {
        try {
            server.connect();
        } catch (UnknownHostException e) {
            throw new OPCException(e);
        } catch (JIException e) {
            throw new OPCException(e);
        } catch (AlreadyConnectedException e) {
            throw new OPCException(e);
        }
    }

    /**try to disconnect the OPC Server*/
    public void disconnect()  {
        server.disconnect();
        itemsCache.clear();
    }

    public void addGroup (String groupName, Collection<? extends String> items) throws OPCException {
        try {
            Group grp = server.addGroup(groupName);
            String[] array =
                    (new ArrayList<String>(items)).toArray(new String[items.size()]);
            Map<String,Item> added = grp.addItems(array);
            itemsCache.put(groupName, added);
        } catch (NotConnectedException e) {
            throw  new OPCException(e);
        } catch (UnknownHostException e) {
            throw  new OPCException(e);
        } catch (JIException e) {
            throw  new OPCException(e);
        } catch (DuplicateGroupException e) {
            throw  new OPCException(e);
        } catch (AddFailedException e) {
            throw  new OPCException(e);
        }
    }

    public Collection<? extends IOPCMessage> readGroup (boolean fromDevice, String groupName) throws OPCException {
        try {

            Group grp = server.findGroup(groupName);
            Map<String, Item> itemCache = itemsCache.get(groupName);
            Collection<Item> items = itemCache.values();
            // populate array
            int cc=0;
            Item[] array = new Item[items.size()];
            for (Item item : items) {
                array[cc++] = item;
            }
            // read values from OPC server
            Map<Item, ItemState> readValues = grp.read(fromDevice, array);
            Set<OPCMessageAdapter> messageAdapters = new HashSet<OPCMessageAdapter>();

            // build adapters
            for (Item i : readValues.keySet())
                messageAdapters.add(new OPCMessageAdapter(i.getId(), readValues.get(i)));
            return messageAdapters;

        } catch (UnknownHostException e) {
            throw  new OPCException(e);
        } catch (JIException e) {
            throw  new OPCException(e);
        } catch (UnknownGroupException e) {
            throw  new OPCException(e);
        } catch (NotConnectedException e) {
            throw  new OPCException(e);
        }
    }

    public void notifyItemChanges(final String itm, final IItemChangeCallback opcProducer) throws OPCException {

        AccessBase accessBase;
        try {

            accessBase = new Async20Access(server,server.getDefaultUpdateRate(), false);
            accessBase.addItem(itm, new DataCallback() {
                @Override
                public void changed(Item item, ItemState itemState) {
                    opcProducer.onChange(new OPCMessageAdapter(item.getId(), itemState));
                }
            });
            accessBase.bind();

        } catch (UnknownHostException e) {
            throw  new OPCException(e);
        } catch (NotConnectedException e) {
            throw  new OPCException(e);
        } catch (JIException e) {
            throw  new OPCException(e);
        } catch (DuplicateGroupException e) {
            throw  new OPCException(e);
        } catch (AddFailedException e) {
            throw  new OPCException(e);
        }


    }

    /**
     * test current connection status
     */
    public boolean isConnected() {
        OPCSERVERSTATUS state = server.getServerState();
        return (state != null && OPCSERVERSTATE.OPC_STATUS_RUNNING.equals(state.getServerState()));
    }
}
