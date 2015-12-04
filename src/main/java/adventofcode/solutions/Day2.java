
package adventofcode.solutions;

import adventofcode.framework.SolverMethod;
import adventofcode.framework.SolverClass;

import java.util.stream.Stream;

/**
 * Solves the problems for day two of the advent calendar:
 * <a href="http://adventofcode.com/day/2">adventofcode.com/day/2</a>
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 2)
public class Day2 {

    @SolverMethod(part = 1)
    public int findTotalAreaOfPaper(String boxDimensions) {
        return Stream.of(boxDimensions.split("\n"))
                     .map(line -> line.split("x"))
                     .map(s -> Stream.of(s).mapToInt(Integer::parseInt).sorted().toArray())
                     .mapToInt(dims -> 2 * (dims[0] * dims[1] + dims[0] * dims[2] + dims[1] * dims[2]) + (dims[0] * dims[1]))
                     .sum();
    }

    @SolverMethod(part = 2)
    public int findTotalFeetOfRibbon(String boxDimensions) {
        return Stream.of(boxDimensions.split("\n"))
                     .map(line -> line.split("x"))
                     .map(s -> Stream.of(s).mapToInt(Integer::parseInt).sorted().toArray())
                     .mapToInt(dims -> 2 * (dims[0] + dims[1]) + (dims[0] * dims[1] * dims[2]))
                     .sum();
    }

}
