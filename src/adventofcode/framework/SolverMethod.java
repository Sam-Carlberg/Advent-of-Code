
package adventofcode.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method marked with this annotation that is a subclass of {@link Solver}
 * will be run when {@link Solver#solve() solve()} is called on an instance of
 * that class. The method should return the solution to that part of the
 * problem.
 *
 * @author Sam Carlberg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SolverMethod {

    /**
     * The part of the challenge being solved. (1, 2, ...)
     */
    int part();

}
