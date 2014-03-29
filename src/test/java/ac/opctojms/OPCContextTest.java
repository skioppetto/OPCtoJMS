package ac.opctojms;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skioppetto on 23/03/14.
 */
public class OPCContextTest implements  IOPCContext {

    String[] tstArray = new String[]{"grp1", "grp2"};
    String[] tstItemsGrp1 = new String[]{"grp1.itm1", "grp1.itm2"};
    String[] tstItemsGrp2 = new String[]{"grp2.itm1", "grp2.itm2"};
    Map<String, String[]> grpMap = new HashMap<String, String[]>();

    private IOPCConnectionInfo connInfo = new OPCConnInfoTest();

    public OPCContextTest(){
        grpMap.put(tstArray[0], tstItemsGrp1);
        grpMap.put(tstArray[1], tstItemsGrp2);
    }

    @Override
    public Iterable<? extends String> getOPCGroupNames() {
        return Arrays.asList(tstArray);
    }

    @Override
    public Collection<? extends String> getOPCGroupItemKeys(String name) {
        return Arrays.asList(grpMap.get(name));
    }

    @Override
    public IOPCConnectionInfo getConnectionInfo() {
        return connInfo;
    }

    @Override
    public IOPCItemInfo getOPCItemInfo(String groupName, String itemName) {
        return null;
    }


}

