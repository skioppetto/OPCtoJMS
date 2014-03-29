package ac.opctojms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCProducer implements IItemChangeCallback {

    private final IOPCContext context;
    private final ServerManager sm;
    private final BlockingQueue<IOPCMessage> queue;
    private final Map<String, String> triggerGroup = new HashMap<String, String>();

    public OPCProducer(IOPCContext map, BlockingQueue<IOPCMessage> queue) throws OPCException {
        this.context = map;
        this.queue = queue;
        this.sm = new ServerManager(map.getConnectionInfo());
        init();
    }

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

    @Override
    public void onChange(IOPCMessage item) {
          OPCMessageGroup messageGroup = new OPCMessageGroup(item);
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
}
