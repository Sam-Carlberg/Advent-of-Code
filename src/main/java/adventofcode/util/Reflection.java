
package adventofcode.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;

/**
 *
 * @author Sam Carlberg
 */
public class Reflection {

    /**
     * Checks if the given class has a default constructor.
     */
    public static boolean hasDefaultConstructor(Class<?> clazz) {
        try {
            return (clazz.getConstructor().getModifiers() & Modifier.PUBLIC) > 0;
        } catch (NoSuchMethodException | SecurityException e) {
            return false;
        }
    }

    /**
     * Instantiates the given class. Used to simplify stream operations.
     */
    public static <T> T instantiate(Class<T> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("No default constructor in " + clazz, ex);
        }
    }
    
    public static Object invoke(Object instance, Method m, Object... args) {
        try {
            return m.invoke(instance, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(String.format("Could not invoke method '%s' on '%s' with arguments '%s'", m, instance, Arrays.toString(args)), ex);
        }
    }

}
