package ac.opctojms;

import java.util.Date;
import java.util.List;

/**
 * Created by skioppetto on 23/03/14.
 */
public class OPCMessageGroup implements IOPCMessage {

    private List<IOPCMessage> items;
    private final IOPCMessage delegate;

    public OPCMessageGroup(IOPCMessage delegate) {
        this.delegate = delegate;
    }

    public List<IOPCMessage> getItems(){
        return items;
    }

    public void setItems(List<IOPCMessage> items) {
        this.items = items;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public int getQuality() {
        return delegate.getQuality();
    }

    @Override
    public String getJsonValue() {
        return delegate.getJsonValue();
    }

    @Override
    public String getDataType() {
        return delegate.getDataType();
    }

    @Override
    public Date getUpdateDate() {
        return delegate.getUpdateDate();
    }
}
