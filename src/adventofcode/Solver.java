
package adventofcode;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract superclass of solvers for daily problems. This expects input files
 * named in the format {@code input_day$day.txt}, where {@code $day} is the day
 * of the advent calendar that this solves for.
 *
 * @author Sam Carlberg
 */
public abstract class Solver {

    private static final String FILE_PATTERN = "inputs/input_day%d.txt";

    private final int day;
    
    protected final String input;

    public Solver() {
        // Set day
        if (getClass().isAnnotationPresent(SolverClass.class)) {
            this.day = getClass().getDeclaredAnnotation(SolverClass.class).day();
        } else {
            throw new RuntimeException("No @SolverClass annotation present on subclass of Solver");
        }

        // Set input
        try {
            input = Files.lines(new File(String.format(FILE_PATTERN, day)).toPath())
                         .collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't open input file for day " + day);
        }
    }

    /**
     * Runs all methods marked with the {@link SolverMethod @SolverMethod}
     * annotation, ordered in ascending order by part number.
     */
    public void solve() {
        Stream.of(getClass().getMethods())
              .filter(m -> m.isAnnotationPresent(SolverMethod.class))
              .filter(m -> m.getParameterCount() == 0)
              .sorted((m1, m2) -> m1.getAnnotation(SolverMethod.class).part() - m2.getAnnotation(SolverMethod.class).part())
              .forEachOrdered(m -> invoke(m));
    }

    private void invoke(Method m) {
        try {
            int part = m.getAnnotation(SolverMethod.class).part();
            Object result = m.invoke(this);
            System.out.printf("Solution for day %d, part %d: %s\n", day, part, result);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            // meh
            throw new RuntimeException(ex);
        }
    }

}
