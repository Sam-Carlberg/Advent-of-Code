
package adventofcode;

import java.util.stream.Stream;

/**
 * Solves the problems for day two of the advent calendar:
 * <a href="http://adventofcode.com/day/2">adventofcode.com/day/2</a>
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 2)
public class Day2 extends Solver {

    @SolverMethod(part = 1)
    public int findTotalAreaOfPaper() {
        return Stream.of(input.split("\n"))
                     .map(line -> line.split("x"))
                     .map(s -> Stream.of(s).mapToInt(Integer::parseInt).sorted().toArray())
                     .mapToInt(dims -> 2 * (dims[0] * dims[1] + dims[0] * dims[2] + dims[1] * dims[2]) + (dims[0] * dims[1]))
                     .sum();
    }

    @SolverMethod(part = 2)
    public int findTotalFeetOfRibbon() {
        return Stream.of(input.split("\n"))
                     .map(line -> line.split("x"))
                     .map(s -> Stream.of(s).mapToInt(Integer::parseInt).sorted().toArray())
                     .mapToInt(dims -> 2 * (dims[0] + dims[1]) + (dims[0] * dims[1] * dims[2]))
                     .sum();
    }

}
