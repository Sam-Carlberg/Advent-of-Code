
package adventofcode.solutions;

import adventofcode.framework.SolverClass;
import adventofcode.framework.SolverMethod;

import java.util.stream.Stream;

/**
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 5)
public class NiceStrings {
    
    @SolverMethod(part = 1)
    public long niceStrings(String inputs) {
        return Stream.of(inputs.split("\n"))
                     .filter(line -> line.matches("^((?!ab|cd|pq|xy).)*$")) // no 'ab', 'cd', 'pq', or 'xy'
                     .filter(line -> line.matches("^.*?(.)(\\1).*?$")) // double letters
                     .filter(line -> line.matches("^.*([aeiou].*){3}$")) // three vowels
                     .count();
    }
    
    @SolverMethod(part = 2)
    public long betterNiceStrings(String input) {
        return Stream.of(input.split("\n"))
                     .filter(line -> line.matches("^.*(.).(\\1).*$")) // matches char sandwich
                     .filter(line -> line.matches("^.*(.{2}).*(\\1).*$")) // matches repeating pair
                     .count();
    }
    
}
