package ac.opctojms;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by skioppetto on 25/03/14.
 */
public class MatrikonSimContext implements IOPCContext{

    private final IOPCConnectionInfo connInfo;

    public MatrikonSimContext(IOPCConnectionInfo info){this.connInfo = info;}

    @Override
    public Iterable<? extends String> getOPCGroupNames() {
        return Arrays.asList("test");
    }

    @Override
    public Collection<? extends String> getOPCGroupItemKeys(String name) {
        return Arrays.asList( "Random.UInt4",
                "Random.UInt1",
                "Random.Time",
                "Random.String",
                "Random.Real8",
                "Random.UInt2",
                "Random.Int4",
                "Random.Money",
                "Random.Qualities",
                "Random.ArrayOfReal8",
                "Random.ArrayOfString",
                "Random.Boolean",
                "Random.Int1",
                "Random.Int2",
                "Random.Real4");
    }

    @Override
    public IOPCConnectionInfo getConnectionInfo() {
        return connInfo;
    }

    @Override
    public IOPCItemInfo getOPCItemInfo(String groupName, String itemName) {
        if (itemName.equals("Random.Boolean"))
            return new IOPCItemInfo() {
                @Override
                public boolean isTrigger() {
                    return true;
                }

                @Override
                public ReturnTypeEnum getReturnMethod() {
                    return ReturnTypeEnum.Group;
                }
            };
        else
            return new IOPCItemInfo() {
                @Override
                public boolean isTrigger() {
                    return false;
                }

                @Override
                public ReturnTypeEnum getReturnMethod() {
                    return ReturnTypeEnum.Group;
                }
            };
    }
}
