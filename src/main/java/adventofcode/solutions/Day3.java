
package adventofcode.solutions;

import adventofcode.util.Tuple;
import adventofcode.framework.SolverMethod;
import adventofcode.framework.SolverClass;

import java.util.HashSet;
import java.util.Set;

import static adventofcode.util.Tuple.tuple;

/**
 * Solves the problems for day three of the advent calendar:
 * <a href="http://adventofcode.com/day/3">adventofcode.com/day/3</a>
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 3)
public class Day3 {
    
    private static final char UP    = '^';
    private static final char DOWN  = 'v';
    private static final char LEFT  = '<';
    private static final char RIGHT = '>';

    @SolverMethod(part = 1)
    public int findHousesVisitedBySanta(String directions) {
        return housesVisited(directions, 1);
    }

    @SolverMethod(part = 2)
    public int findHousesVisitedBySantaAndRobot(String directions) {
        return housesVisited(directions, 2);
    }
    
    private int housesVisited(String directions, int numActors) {
        Set<Tuple> visited = new HashSet<>();
        int[] x = new int[numActors];
        int[] y = new int[numActors];
        char[] chars = directions.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int s = i % numActors;
            switch (c) {
                case UP:    x[s]++; break;
                case DOWN:  x[s]--; break;
                case LEFT:  y[s]--; break;
                case RIGHT: y[s]++; break;
            }
            visited.add(tuple(x[s], y[s]));
        }
        return visited.size();
    }

}
