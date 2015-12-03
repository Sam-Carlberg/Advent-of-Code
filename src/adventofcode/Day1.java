
package adventofcode;

/**
 * Solves the problems for day one of the advent calendar:
 * <a href="http://adventofcode.com/day/1">adventofcode.com/day/1</a>
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 1)
public class Day1 extends Solver {

    @SolverMethod(part = 1)
    public int findFinalFloor() {
        int leftParens = input.replaceAll("[^(]", "").length();
        int rightParens = input.replaceAll("[^)]", "").length();
        return leftParens - rightParens;
    }

    @SolverMethod(part = 2)
    public int findFirstMoveToBasement() {
        int pos = 0;
        for (int level = 0; pos < input.length() && level > -1; pos++) {
            level += input.charAt(pos) == '(' ? 1 : -1;
        }
        return pos;
    }

}
