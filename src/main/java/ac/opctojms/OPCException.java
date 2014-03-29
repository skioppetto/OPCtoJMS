package ac.opctojms;

import org.openscada.opc.lib.common.NotConnectedException;

import java.util.concurrent.ExecutionException;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCException extends Exception {
    public OPCException(Exception e) {
        super (e);
    }
}
