
package adventofcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

/**
 * Abstract superclass of solvers for daily problems. This expects input files
 * named in the format {@code input_day$day.txt}, where {@code $day} is the day
 * of the advent calendar that this solves for.
 *
 * @author Sam Carlberg
 */
public abstract class Solver<T> {

    private static final String FILE_PATTERN = "inputs/input_day%DAY%.txt";

    private final int day;

    /**
     * Creates a solver for the problem for the given day.
     *
     * @param day the day this solver solves for, in the range (1, 25)
     */
    public Solver(int day) {
        this.day = day;
    }

    /**
     * Solves part 1 of the problem for the given input
     *
     * @param input
     * @return
     */
    protected abstract T part1(String input);

    /**
     * Solves part 2 of the problem for the given input
     *
     * @param input
     * @return
     */
    protected abstract T part2(String input);

    /**
     * Solves both parts of the problem. This reads the text in the input file
     * and passes it to each of {@link #part1(java.lang.String) part1()} and
     * {@link #part2(java.lang.String) part2()}
     */
    public void solve() {
        try {
            String text = Files.lines(new File(FILE_PATTERN.replace("%DAY%", "" + day)).toPath())
                    .collect(Collectors.joining("\n"));
            System.out.println("Solution for day " + day + ", part 1: " + part1(text));
            System.out.println("Solution for day " + day + ", part 2: " + part2(text));
        } catch (IOException ex) {
            System.err.println("Couldn't open input file for day " + day);
        }
    }

}
