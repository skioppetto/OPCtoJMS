package ac.opctojms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by skioppetto on 25/03/14.
 */
public class ServerManagerTest {

    private MatrikonSimLocalConnInfo connInfo = new MatrikonSimLocalConnInfo ();
    private ServerManager sm = new ServerManager(connInfo);

    String[] testItemsSimple = new String[]{
    "Random.ArrayOfReal8",
    "Random.ArrayOfString",
    "Random.Boolean",
    "Random.Int1",
    "Random.UInt1",
    "Random.Int2",
    "Random.Int4",
    "Random.Money",
    "Random.Qualities",
    "Random.Real4",
    "Random.Real8",
    "Random.String",
    "Random.Time",
    "Random.UInt2",
    "Random.UInt4"};


    @Before
    public void setUpConnection () throws Throwable {
        sm.connect();
        Assert.assertTrue(sm.isConnected());
    }

    @After
    public void setDownConnection() throws  Throwable{
        sm.disconnect();
        Assert.assertFalse(sm.isConnected());
    }

    @Test
    public void testConnection () throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    public void testAddAndReadGroup () throws OPCException {

        sm.addGroup("testGroup", Arrays.asList(testItemsSimple));
        Iterable<? extends IOPCMessage> values = sm.readGroup(true, "testGroup");

        int count = 0;
        for (IOPCMessage val : values) {
            Assert.assertTrue(Arrays.asList(testItemsSimple).contains(val.getName()));
            Assert.assertNotNull(val.getJsonValue());
            count++;
        }
        Assert.assertEquals(testItemsSimple.length, count);
    }

}
