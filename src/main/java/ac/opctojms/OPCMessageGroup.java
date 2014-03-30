package ac.opctojms;

import java.util.Collection;
import java.util.Date;

/**
 * Created by skioppetto on 23/03/14.
 */
public class OPCMessageGroup implements IOPCMessage {

    private Collection<? extends IOPCMessage> items;
    private final IOPCMessage delegate;

    public OPCMessageGroup(IOPCMessage delegate) {
        this.delegate = delegate;
    }

    public Collection<? extends IOPCMessage> getItems(){
        return items;
    }

    public void setItems(Collection<? extends IOPCMessage> items) {
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
