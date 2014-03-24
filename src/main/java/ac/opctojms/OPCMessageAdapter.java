package ac.opctojms;

import com.google.gson.Gson;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIString;
import org.openscada.opc.lib.da.ItemState;

import java.util.Date;

/**
 * Created by skioppetto on 23/03/14.
 */
public class OPCMessageAdapter implements IOPCMessage {

    private final ItemState state;
    private final String name;

    private final Gson gson = new Gson();

    public OPCMessageAdapter(String name, ItemState state) {
        this.state = state;
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int getQuality() {
        return state.getQuality();
    }

    @Override
    public String getDataType() {
        try {
            return JIObjectsUtil.getDataType(state.getValue());
        } catch (JIException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public Date getUpdateDate() {
        return state.getTimestamp().getTime();
    }


    @Override
    public String getJsonValue() {
        try {
            Object val = state.getValue().getObject();
            if (val instanceof JIArray){
                JIArray arr = (JIArray) val;
                return gson.toJson(JIObjectsUtil.convertArray(arr));
            }else if (val instanceof  JIString){
                return gson.toJson(((JIString) val).getString());
            }
            else return gson.toJson(val);
        } catch (JIException e) {
            throw new RuntimeException(e);
        }
    }


}
