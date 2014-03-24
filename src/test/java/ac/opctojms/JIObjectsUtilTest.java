package ac.opctojms;

import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIString;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by skioppetto on 23/03/14.
 */
public class JIObjectsUtilTest {


    @Test
    public void testConvertArrayLong(){
        Long[] test = new Long[]{45L, 44L, 23L};
        JIArray array = new JIArray(test);
        Object[] vals = JIObjectsUtil.convertArray(array);
        Assert.assertTrue(Arrays.equals(test, vals));
    }

    @Test
    public void testConvertArrayStrings(){
        String[] expect = new String[]{"abc","def","ghi"};
        JIString[] test = new JIString[]{new JIString(expect[0]), new JIString(expect[1]), new JIString(expect[2])};
        JIArray array = new JIArray(test);
        Object[] vals = JIObjectsUtil.convertArray(array);
        Assert.assertEquals(expect.length, vals.length);
        Assert.assertEquals(expect[0], vals[0]);
        Assert.assertEquals(expect[1], vals[1]);
        Assert.assertEquals(expect[2], vals[2]);
    }

    @Test
    public void testConvertEmptyArray(){
        Integer[] expected = new Integer[]{};
        JIArray array = new JIArray(expected);
        Object[] vals = JIObjectsUtil.convertArray(array);
        Assert.assertEquals(expected.length, vals.length);
    }


}
