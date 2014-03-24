package ac.opctojms;

import org.openscada.opc.lib.common.ConnectionInformation;

/**
 * Created by skioppetto on 23/03/14.
 */
class ConnectionInformationFactory {

    /**build a new ConnectionInformationFactory*/
    public static ConnectionInformation build (IOPCConnectionInfo info){
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost(info.getServerHost());
        ci.setClsid(info.getServerClsid());
        ci.setProgId(info.getServerProgId());
        ci.setUser(info.getServerOsUser());
        ci.setPassword(info.getServerOsPassword());
        ci.setDomain(info.getServerOsDomain());
        return ci;
    }

}
