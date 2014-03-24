package ac.opctojms;

import ac.opctojms.IOPCConnectionInfo;
import ac.opctojms.IOPCContext;

/**
 * Created by skioppetto on 23/03/14.
 */
public class OPCConnInfoTest implements IOPCConnectionInfo {
    @Override
    public String getServerHost() {
        return "localhost";
    }

    @Override
    public String getServerClsid() {
        return "20D04FE0-3AEA-1069-A2D8-08002B30309D";
    }

    @Override
    public String getServerProgId() {
        return "Word.Document.8";
    }

    @Override
    public String getServerOsUser() {
        return "Administrator";
    }

    @Override
    public String getServerOsPassword() {
        return "admin";
    }

    @Override
    public String getServerOsDomain() {
        return "";
    }

}
