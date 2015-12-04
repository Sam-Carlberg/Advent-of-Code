
package adventofcode.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.file.Files;

import java.util.stream.Collectors;

/**
 * Abstract superclass of solvers for daily problems. This expects input files
 * named in the format {@code input_day$day.txt}, where {@code $day} is the day
 * of the advent calendar that this solves for.
 *
 * @author Sam Carlberg
 */
public abstract class Solver {

    private static final String FILE_PATTERN = "/inputs/input_day%d.txt";

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
        try (BufferedReader r = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(String.format(FILE_PATTERN, day))))) {
            input = r.lines().collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't open input file for day " + day);
        }
    }

}
