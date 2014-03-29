package ac.opctojms;

import java.util.Date;

/**
 * Created by skioppetto on 23/03/14.
 */
public interface IOPCMessage {

    /**@return new OPC item name*/
    String getName();
    /**@return new OPC short code for quality.
     * The meaning dipends on Server implementation.*/
    int getQuality();
    String getJsonValue();
    String getDataType();
    Date getUpdateDate();


}
