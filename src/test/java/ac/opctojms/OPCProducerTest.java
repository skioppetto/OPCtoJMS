package ac.opctojms;

import org.junit.Before;
import org.junit.Test;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;
import static org.easymock.EasyMock.*;

/**
 * Created by skioppetto on 21/03/14.
 */
public class OPCProducerTest {

    Server srvMock;

    @Before
    public void setUpServer (){
         srvMock = createMock(Server.class);

    }

    @Test
    public void testBuild(){

        // behavoir


    }


}
