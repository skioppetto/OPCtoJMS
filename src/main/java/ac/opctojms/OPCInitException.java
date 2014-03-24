package ac.opctojms;

import org.openscada.opc.lib.common.NotConnectedException;

import java.util.concurrent.ExecutionException;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCInitException extends Exception {
    public OPCInitException(Exception e) {
        super (e);
    }
}
