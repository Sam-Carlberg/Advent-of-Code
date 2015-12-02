
package adventofcode;

/**
 * Solves the problems for day one of the advent calendar:
 * <a href="http://adventofcode.com/day/1">adventofcode.com/day/1</a>
 *
 * @author Sam Carlberg
 */
public class Day1 extends Solver<Integer> {

    public Day1() {
        super(1);
    }

    @Override
    protected Integer part1(String input) {
        int leftParens = input.replaceAll("[^(]", "").length();
        int rightParens = input.replaceAll("[^)]", "").length();
        return leftParens - rightParens;
    }

    @Override
    protected Integer part2(String input) {
        int pos = 0;
        for (int level = 0; pos < input.length() && level > -1; pos++) {
            level += input.charAt(pos) == '(' ? 1 : -1;
        }
        return pos;
    }

    public static void main(String[] args) {
        new Day1().solve();
    }

}
