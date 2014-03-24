package ac.opctojms;

import java.util.Date;

/**
 * Created by skioppetto on 23/03/14.
 */
public interface IOPCMessage {

    String getName();
    int getQuality();
    String getJsonValue();
    String getDataType();
    Date getUpdateDate();


}
