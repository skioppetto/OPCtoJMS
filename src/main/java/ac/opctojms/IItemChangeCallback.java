package ac.opctojms;

/**
 * Created by skioppetto on 29/03/14.
 */
public interface IItemChangeCallback {

    /**callback function to manage OPC item changes*/
    void onChange(IOPCMessage item);
}
