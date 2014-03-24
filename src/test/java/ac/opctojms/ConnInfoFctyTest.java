package ac.opctojms;

import org.junit.Assert;
import org.junit.Test;
import org.openscada.opc.lib.common.ConnectionInformation;

/**
 * Created by skioppetto on 23/03/14.
 */
public class ConnInfoFctyTest {

    @Test
    public void testBuilder (){
        OPCConnInfoTest tst = new OPCConnInfoTest();
        ConnectionInformation ci = ConnectionInformationFactory.build(tst);
        Assert.assertNotNull(ci);
        Assert.assertEquals(tst.getServerClsid(), ci.getClsid());
        Assert.assertEquals(tst.getServerHost(), ci.getHost());
        Assert.assertEquals(tst.getServerOsDomain(), ci.getDomain());
        Assert.assertEquals(tst.getServerOsPassword(), ci.getPassword());
        Assert.assertEquals(tst.getServerProgId(),ci.getProgId());
        Assert.assertEquals(tst.getServerOsUser(), ci.getUser());
    }

}
