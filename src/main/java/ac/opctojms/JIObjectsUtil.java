package ac.opctojms;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;

/**
 * Created by skioppetto on 23/03/14.
 * This class contains some util methods to manage JInterop objects
 * during adapting
 */
public final class JIObjectsUtil {
    /** convert JIArray to Object[]. It needs some tricks if array is made of JIString  */
    public static Object[] convertArray (JIArray array){
        Object[] arrayInstance = (Object[]) array.getArrayInstance();
        // if made of JIString needs to convert all objects to String
        if (JIString.class.equals(array.getArrayClass())){
            String[] retValues = new String[array.getUpperBounds()[0]];
            for (int i=0; i< array.getUpperBounds()[0]; i++){
                retValues[i] =  ((JIString)arrayInstance[i]).getString();
            }
            return retValues;
        }
        return arrayInstance;
    }

    /**calculate the dataType expression of a JIVariant.
     * @return the class simpleName for primitive types ("Integer", "Boolean", "String"...).
     * and a class simpleName followed by brackets for array types ("Integer[]", "Boolean[]")*/
    public static String getDataType(JIVariant var) throws JIException {
        Object obj = var.getObject();
        if (obj instanceof JIArray){
            JIArray arr = (JIArray) obj;
            return getSimpleClz(arr.getArrayClass()).concat("[]");
        }else return getSimpleClz(obj.getClass());
    }
    // utility method
    private static String getSimpleClz (Class clz){
        if (JIString.class.equals(clz))
            return String.class.getSimpleName();
        else return clz.getSimpleName();
    }
}
