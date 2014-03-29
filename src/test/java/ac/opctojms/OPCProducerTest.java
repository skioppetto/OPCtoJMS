package ac.opctojms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCProducerTest {

    private BlockingQueue<IOPCMessage> queue = new ArrayBlockingQueue<IOPCMessage>(50);

    private OPCProducer producer;

    @Before
    public void setUp() throws OPCException {
        producer = new OPCProducer(new MatrikonSimContext(new MatrikonSimLocalConnInfo()), queue);
    }

    @After
    public void disconnect() {
        producer.disconnect();
        Assert.assertFalse(producer.isConnected());
    }

    @Test
    public void testEvent() throws OPCException, InterruptedException {
        Assert.assertTrue(producer.isConnected());
        Thread.sleep(5000);
        Assert.assertFalse(queue.isEmpty());
        OPCMessageGroup latest = (OPCMessageGroup) queue.peek();
        Assert.assertEquals("Random.Boolean", latest.getName());
        Assert.assertNotNull(latest.getItems());
        Assert.assertTrue(latest.getItems().size() > 0);
    }

    @Test
    public void testReconnect() throws OPCException, InterruptedException {
        producer.disconnect();
        Assert.assertFalse(producer.isConnected());
        producer.connect();
        testEvent();
    }

    @Test
    public void testCheckConnectionStatusBeforeConnect() throws OPCException {
        // this call throws an exception if is not
        // controlled current connection status
        producer.connect();
        Assert.assertTrue(producer.isConnected());
    }


}
