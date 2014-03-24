package ac.opctojms;

import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.junit.Assert;
import org.junit.Test;
import org.openscada.opc.lib.da.ItemState;

import java.util.Calendar;

/**
 * Created by skioppetto on 23/03/14.
 */
public class OPCMessageAdapterTest {

    @Test
    public void testAdaptInt(){
        JIVariant ji = new JIVariant(10, false);
        ItemState is = new ItemState();
        Calendar testDate = Calendar.getInstance();
        is.setValue(ji);
        is.setQuality((short) 192);
        is.setTimestamp(testDate);
        OPCMessageAdapter message = new OPCMessageAdapter("test", is);
        Assert.assertEquals("test", message.getName());
        Assert.assertEquals(192, message.getQuality());
        Assert.assertEquals("Integer", message.getDataType());
        Assert.assertEquals("10", message.getJsonValue());
        Assert.assertEquals(testDate.getTime(), message.getUpdateDate());
    }

    @Test
    public void testAdaptString(){
        JIVariant ji = new JIVariant("bla", false);
        ItemState is = new ItemState();
        Calendar testDate = Calendar.getInstance();
        is.setValue(ji);
        is.setQuality((short) 192);
        is.setTimestamp(testDate);
        OPCMessageAdapter message = new OPCMessageAdapter("test", is);
        Assert.assertEquals("test", message.getName());
        Assert.assertEquals(192, message.getQuality());
        Assert.assertEquals("String", message.getDataType());
        Assert.assertEquals("\"bla\"", message.getJsonValue());
        Assert.assertEquals(testDate.getTime(), message.getUpdateDate());
    }

    @Test
    public void testAdaptBoolean(){
        JIVariant ji = new JIVariant(false, false);
        ItemState is = new ItemState();
        Calendar testDate = Calendar.getInstance();
        is.setValue(ji);
        is.setQuality((short) 192);
        is.setTimestamp(testDate);
        OPCMessageAdapter message = new OPCMessageAdapter("test", is);
        Assert.assertEquals("test", message.getName());
        Assert.assertEquals(192, message.getQuality());
        Assert.assertEquals("Boolean", message.getDataType());
        Assert.assertEquals("false", message.getJsonValue());
        Assert.assertEquals(testDate.getTime(), message.getUpdateDate());
    }

    @Test
    public void testAdaptCollectionInteger(){
        JIVariant ji = new JIVariant(new JIArray(new Integer[]{13, 14, 15}), false);
        ItemState is = new ItemState();
        Calendar testDate = Calendar.getInstance();
        is.setValue(ji);
        is.setQuality((short) 192);
        is.setTimestamp(testDate);
        OPCMessageAdapter message = new OPCMessageAdapter("test", is);
        Assert.assertEquals("test", message.getName());
        Assert.assertEquals(192, message.getQuality());
        Assert.assertEquals("Integer[]", message.getDataType());
        Assert.assertEquals("[13,14,15]", message.getJsonValue());
        Assert.assertEquals(testDate.getTime(), message.getUpdateDate());
    }

    @Test
    public void testAdaptCollectionString(){
        JIVariant ji = new JIVariant(new JIArray(new JIString[]{new JIString("13"), new JIString("14"), new JIString("15")}), false);
        ItemState is = new ItemState();
        Calendar testDate = Calendar.getInstance();
        is.setValue(ji);
        is.setQuality((short) 192);
        is.setTimestamp(testDate);
        OPCMessageAdapter message = new OPCMessageAdapter("test", is);
        Assert.assertEquals("test", message.getName());
        Assert.assertEquals(192, message.getQuality());
        Assert.assertEquals("String[]", message.getDataType());
        Assert.assertEquals("[\"13\",\"14\",\"15\"]", message.getJsonValue());
        Assert.assertEquals(testDate.getTime(), message.getUpdateDate());
    }
}
