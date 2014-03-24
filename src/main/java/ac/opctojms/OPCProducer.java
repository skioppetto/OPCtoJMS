package ac.opctojms;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCProducer implements DataCallback {

    private final Logger log = Logger.getLogger(OPCProducer.class.getName());

    //DEFAULTS
    private static final int RECONNECT_DELAY_DEFAULT = 1000;

    private Server srv;
    private final IOPCContext context;

    // used by triggers to get the reading group
    private Map<String, Group> trigger2groups = new HashMap<String, Group>();
    private Map<Group, Item[]>  group2items = new HashMap<Group, Item[]>();

    // set of async access controls
    private Set<AccessBase> accessHandles = new HashSet<AccessBase>();

    // queue
    private final BlockingQueue<IOPCMessage> queue;

    public OPCProducer(IOPCContext map, BlockingQueue<IOPCMessage> queue) throws OPCInitException {
        this.context = map;
        this.queue = queue;
        init();
    }

    protected void init() throws OPCInitException {
        try {
            // build a connectionInformation object
            ConnectionInformation ci = ConnectionInformationFactory.build(context.getConnectionInfo());
            // create and connect the server
            srv = new Server(ci, Executors.newSingleThreadScheduledExecutor());
            AutoReconnectController srvController = new AutoReconnectController(srv, RECONNECT_DELAY_DEFAULT);
            srvController.connect();
            // create async access for each group and the reading group too
            //TODO: add to context IOPCGroupInfo to define group grained config
            for (String grpName : context.getOPCGroupNames()){
                AccessBase ab = new Async20Access(srv, srv.getDefaultUpdateRate(), false);
                accessHandles.add(ab);
                // init the reading group
                Group readingGrp = srv.addGroup(grpName);
                List<Item> readingItems = new ArrayList<Item>();
                for (String itemName : context.getOPCGroupItemKeys(grpName)){
                    IOPCItemInfo itemInfo = context.getOPCItemInfo(grpName, itemName);
                    if (itemInfo.isTrigger()){
                        // on async group add "grpName.itemName" as I need a global unique name
                        String trgName = buildTriggerName(grpName, itemName);
                        ab.addItem(trgName, this);
                        trigger2groups.put(trgName, readingGrp);
                    }
                    // add here items to reading items
                    readingGrp.addItem(itemName);
                }
                // put the reading group items list
                group2items.put(readingGrp, readingItems.toArray(new Item[readingItems.size()]));
            }
        } catch (NotConnectedException e) {
            throw new OPCInitException (e);
        } catch (UnknownHostException e) {
            throw new OPCInitException (e);
        } catch (JIException e) {
            throw new OPCInitException (e);
        } catch (DuplicateGroupException e) {
            throw new OPCInitException (e);
        } catch (AddFailedException e) {
            throw new OPCInitException (e);
        }
    }

    public void start(){
        for (AccessBase handle : accessHandles) {
            handle.bind();
        }
    }

    public void stop() {
        for (AccessBase handle : accessHandles) {
            if (handle.isBound())
                try {
                    handle.unbind();
                } catch (JIException e) {
                    log.log(Level.SEVERE, "cannot stop async group", e);
                }
        }
        srv.disconnect();
    }

    /* trigger names must be unique */
    private static String buildTriggerName (String grp, String itm){
        return grp + "." + itm;
    }

    /* split trigger name to get group and item name*/
    private static String[] splitTriggerName (String trg){
        return trg.split(".");
    }

    @Override
    public void changed(Item item, ItemState itemState) {
        String[] splittedTrg = splitTriggerName(item.getId());
        IOPCItemInfo info = context.getOPCItemInfo(splittedTrg[0],splittedTrg[1]);
        if (info.isTrigger()){
            try {
                IOPCMessage trgMsg = new OPCMessageAdapter(item.getId(), itemState);
                IOPCMessage msg = trgMsg;
                switch(info.getReturnMethod()){
                    case Group:
                        OPCMessageGroup grpMsg = new OPCMessageGroup(trgMsg);
                        // get the reading group from the trigger name
                        Group group = trigger2groups.get(item.getId());
                        // get the item list in memory from the reading group
                        Map<Item, ItemState> values = group.read(false, group2items.get(group));
                        // build a new message for each item in group
                        List<IOPCMessage> messageItems = new ArrayList<IOPCMessage>();
                        for (Item itm : values.keySet()){
                            messageItems.add(new OPCMessageAdapter(itm.getId(), values.get(itm)));
                        }
                        grpMsg.setItems(messageItems);
                        msg = grpMsg;
                        break;
                    case Item:
                        break;
                }
                queue.put(msg);
            } catch (JIException e) {
                log.log(Level.SEVERE, "error on change event", e);
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, "losing a message", e);
            }
        }
    }
}
