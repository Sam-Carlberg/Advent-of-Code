
package adventofcode;

import java.util.stream.Stream;

/**
 * Solves the problems for day two of the advent calendar:
 * <a href="http://adventofcode.com/day/2">adventofcode.com/day/2</a>
 *
 * @author Sam Carlberg
 */
public class Day2 extends Solver<Integer> {

    public Day2() {
        super(2);
    }

    @Override
    protected Integer part1(String input) {
        return Stream.of(input.split("\n"))
                     .map(line -> line.split("x"))
                     .map(s -> Stream.of(s).mapToInt(Integer::parseInt).sorted().toArray())
                     .mapToInt(dims -> 2 * (dims[0] * dims[1] + dims[0] * dims[2] + dims[1] * dims[2]) + (dims[0] * dims[1]))
                     .sum();
    }

    @Override
    protected Integer part2(String input) {
        return Stream.of(input.split("\n"))
                     .map(line -> line.split("x"))
                     .map(s -> Stream.of(s).mapToInt(Integer::parseInt).sorted().toArray())
                     .mapToInt(dims -> 2 * (dims[0] + dims[1]) + (dims[0] * dims[1] * dims[2]))
                     .sum();
    }

    public static void main(String[] args) {
        new Day2().solve();
    }

}
