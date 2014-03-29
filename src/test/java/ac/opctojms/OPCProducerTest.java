package ac.opctojms;

import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCProducerTest {

    private BlockingQueue<IOPCMessage> queue = new ArrayBlockingQueue<IOPCMessage>(50);

    @TestSubject
    private OPCProducer producer;

    @Test
    public void testEvent() throws OPCException, InterruptedException {
        producer = new OPCProducer(new MatrikonSimContext(new MatrikonSimLocalConnInfo()), queue);
        Thread.sleep(5000);
        Assert.assertFalse(queue.isEmpty());
        OPCMessageGroup latest = (OPCMessageGroup) queue.peek();
        Assert.assertEquals("Random.Boolean", latest.getName());
        Assert.assertNotNull(latest.getItems());
        Assert.assertTrue(latest.getItems().size() > 0);
    }


}
