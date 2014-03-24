package ac.opctojms;

import org.openscada.opc.lib.da.Server;

import java.util.Collection;
import java.util.List;

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
    Iterable<? extends String> getOPCGroupItemKeys(String name);

    /**@return a @see{IOPCConnectionInfo.class}*/
    IOPCConnectionInfo getConnectionInfo();

    /**@return a @see{IOPCItemInfo.class} from an item key
     * @param groupName
     * @param itemName*/
    IOPCItemInfo getOPCItemInfo(String groupName, String itemName);
}
