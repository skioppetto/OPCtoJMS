package ac.opctojms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCProducer implements IItemChangeCallback {

    private final IOPCContext context;
    private final ServerManager sm;
    private final BlockingQueue<OPCMessage> queue;
    private final Map<String, String> triggerGroup = new HashMap<String, String>();

    /**constructor build and init the object.
     * After built it starts listening to OPC server changes*/
    public OPCProducer(IOPCContext map, BlockingQueue<OPCMessage> queue) throws OPCException {
        this.context = map;
        this.queue = queue;
        this.sm = new ServerManager(map.getConnectionInfo());
        init();
    }

    /*init logic can be overriden.*/
    protected void init() throws OPCException {
        sm.connect();
        //TODO: add to context IOPCGroupInfo to define group grained config
        for (String grpName : context.getOPCGroupNames()){
            Collection<? extends String> items = context.getOPCGroupItemKeys(grpName);
            sm.addGroup(grpName, context.getOPCGroupItemKeys(grpName));
            for (String itm : items){
                IOPCItemInfo itmInfo = context.getOPCItemInfo(grpName, itm);
                if (itmInfo != null && itmInfo.isTrigger()) {
                    sm.notifyItemChanges(itm, this);
                    triggerGroup.put(itm, grpName);
                }
            }
        }
    }

    /**this public method should not be used by clients.
     * It's a callback to manage OPC messages reception*/
    @Override
    public void onChange(IOPCMessage item) {
        OPCMessage messageGroup = new OPCMessage(item);
        String groupName = triggerGroup.get(item.getName());
        try {
            Collection<? extends IOPCMessage> group = sm.readGroup(false, groupName);
            messageGroup.setItems(group);
            queue.put(messageGroup);
        } catch (OPCException e) {
            Logger.getLogger(OPCProducer.class.getName()).log(Level.SEVERE, "Cannot read group from OPC server.",e);
        } catch (InterruptedException e) {
            Logger.getLogger(OPCProducer.class.getName()).log(Level.SEVERE, "Interrupted while sending to queue.", e);
        }
    }

    /**
     * this method disconnects from the OPC server
     */
    public void disconnect() {
        if (sm.isConnected()) {
            sm.disconnect();
            triggerGroup.clear();
        }
    }

    /**
     * this method connect after checking current connection status
     */
    public void connect() throws OPCException {
        if (!sm.isConnected())
            init();
    }

    public boolean isConnected() {
        return sm.isConnected();
    }
}
