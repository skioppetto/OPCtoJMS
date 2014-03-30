package ac.opctojms;

import java.util.Collection;

/**
 * Created by skioppetto on 21/03/14.
 * This interface define contexts data needed to configure an OPC Server
 *
 */
public interface IOPCContext {

    /**@return OPC group names. A group is made of a set of Items. OPCProducer
     * will listen to item changes and will return the Group snapshot*/
    Iterable<? extends String> getOPCGroupNames();

    /**@return OPC Items by Group Name*/
    Collection<? extends String> getOPCGroupItemKeys(String name);

    /**@return a @see{IOPCConnectionInfo.class}*/
    IOPCConnectionInfo getConnectionInfo();

    /**@return a @see{IOPCItemInfo.class} from an item key
     * @param groupName group name
     * @param itemName item name */
    IOPCItemInfo getOPCItemInfo(String groupName, String itemName);
}
