
package adventofcode.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Sam Carlberg
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SolverClass {

    int day();

}
