package ac.opctojms;

/**
 * Created by skioppetto on 23/03/14.
 */
public interface IOPCConnectionInfo {

    /**@return OPC server hostname. OPC servers on remote hosts need to be configured
     * property to let DCOM work */
    String getServerHost();

    /**@return OPC server clsid. example: "SWToolbox.TOPServer.V5" */
    String getServerClsid();

    /**@return OPC server progId. example: "680DFBF7-C92D-484D-84BE-06DC3DECCD68".
     * if ProgId is not working, try it using the Clsid instead */
    String getServerProgId();

    /**@return Os user*/
    String getServerOsUser();

    /**@return Os password*/
    String getServerOsPassword();

    /**@return Os domain. Empty string if not set*/
    String getServerOsDomain();

}
