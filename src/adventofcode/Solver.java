
package adventofcode;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.nio.file.Files;

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

    private static final String FILE_PATTERN = "inputs/input_day%DAY%.txt";

    private final int day;
    
    protected String input;

    public Solver() {
        if (getClass().isAnnotationPresent(SolverClass.class)) {
            this.day = getClass().getDeclaredAnnotation(SolverClass.class).day();
        } else {
            throw new RuntimeException("No @Day annotation present on subclass of Solver");
        }
    }

    /**
     * Runs all methods marked with the {@link SolverMethod @SolverMethod}
     * annotation, ordered in ascending order by part number.
     */
    public void solve() {
        try {
            input = Files.lines(new File(FILE_PATTERN.replace("%DAY%", "" + day)).toPath())
                         .collect(Collectors.joining("\n"));
            Stream.of(getClass().getMethods())
                  .filter(m -> m.isAnnotationPresent(SolverMethod.class))
                  .filter(m -> m.getParameterCount() == 0)
                  .sorted((m1, m2) -> m1.getAnnotation(SolverMethod.class).part() - m2.getAnnotation(SolverMethod.class).part())
                  .forEachOrdered(m -> invoke(m));
        } catch (IOException ex) {
            System.err.println("Couldn't open input file for day " + day);
        }
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
