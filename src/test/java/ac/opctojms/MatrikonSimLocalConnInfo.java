package ac.opctojms;

/**
 * Created by skioppetto on 25/03/14.
 */
public class MatrikonSimLocalConnInfo implements IOPCConnectionInfo {
    private  String osUser = "skioppetto";
    private  String osPassword = "tizzone";

    public MatrikonSimLocalConnInfo(){}

    public MatrikonSimLocalConnInfo(String osUser, String osPassword){
        this.osPassword = osPassword;
        this.osUser = osUser;
    }

    @Override
    public String getServerHost() {
        return "localhost";
    }

    @Override
    public String getServerClsid() {
      // return "F8582CF3-88FB-11D0-B850-00C0F0104305";
       return "F8582CF2-88FB-11D0-B850-00C0F0104305";
    //    return "";
    }

    @Override
    public String getServerProgId() {
        return "Matrikon.OPC.Simulation.1";
    }

    @Override
    public String getServerOsUser() {
        return osUser;
    }

    @Override
    public String getServerOsPassword() {
        return osPassword;
    }

    @Override
    public String getServerOsDomain() {
        return "";
    }
}
