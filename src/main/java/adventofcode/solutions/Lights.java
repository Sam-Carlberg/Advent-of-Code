
package adventofcode.solutions;

import adventofcode.framework.SolverClass;
import adventofcode.framework.SolverMethod;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Solves the problems for day six of the advent calendar:
 * <a href="http://adventofcode.com/day/6">adventofcode.com/day/6</a>
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 6)
public class Lights {

    /**
     * group 1 = op
     *
     * group 2 = start
     *
     * group 3 = end
     */
    private final String regex = "^(.*?) ([0-9]+,[0-9]+) through ([0-9]+,[0-9]+)$";

    @SolverMethod(part = 1)
    public int numLit(String instructions) {
        Boolean[][] lights = new Boolean[1000][1000];
        deepFill(lights, () -> false);
        AtomicInteger litCounter = new AtomicInteger();
        Stream.of(instructions.split("\n"))
                .forEach(instruction
                        -> doThing(
                                   instruction,
                                   lights,
                                   l -> l,
                                   l -> !l,
                                   l -> l,
                                   (l, x, y) -> { l[x][y] = false; litCounter.decrementAndGet(); },
                                   (l, x, y) -> { l[x][y] = true;  litCounter.incrementAndGet(); },
                                   (l, x, y) -> { l[x][y] = false; litCounter.decrementAndGet(); },
                                   (l, x, y) -> { l[x][y] = true;  litCounter.incrementAndGet(); }
                        )
                );
        return litCounter.get();
    }

    @SolverMethod(part = 2)
    public int brightness(String instructions) {
        Integer[][] lights = new Integer[1000][1000];
        deepFill(lights, () -> 0);
        AtomicInteger brightnessCounter = new AtomicInteger();
        Stream.of(instructions.split("\n"))
                .forEach(instruction
                        -> doThing(
                                   instruction,
                                   lights,
                                   l -> l > 0,
                                   l -> true,
                                   l -> true,
                                   (l, x, y) -> { l[x][y]--;    brightnessCounter.decrementAndGet(); },
                                   (l, x, y) -> { l[x][y]++;    brightnessCounter.incrementAndGet(); },
                                   (l, x, y) -> { l[x][y] += 2; brightnessCounter.addAndGet(2); },
                                   TriConsumer.NONE
                        )
                );
        return brightnessCounter.get();
    }
    
    private static <T> void deepFill(T[][] arr, Supplier<T> s) {
        for (T[] sub : arr) {
            for (int i = 0; i < sub.length; i++) {
                sub[i] = s.get();
            }
        }
    }

    private <T> void doThing(String instruction,
            T[][] lights,
            Predicate<T> checkOff,
            Predicate<T> checkOn,
            Predicate<T> checkToggle,
            TriConsumer<T[][], Integer, Integer> cOff,
            TriConsumer<T[][], Integer, Integer> cOn,
            TriConsumer<T[][], Integer, Integer> cToggleTrue,
            TriConsumer<T[][], Integer, Integer> cToggleFalse) {
        String op = getOp(instruction);
        int startX = getStart(instruction)[0];
        int startY = getStart(instruction)[1];
        int endX = getEnd(instruction)[0];
        int endY = getEnd(instruction)[1];
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                switch (op) {
                    case "turn off":
                        if (checkOff.test(lights[x][y])) {
                            cOff.accept(lights, x, y);
                        }
                        break;
                    case "turn on":
                        if (checkOn.test(lights[x][y])) {
                            cOn.accept(lights, x, y);
                        }
                        break;
                    case "toggle":
                        if (checkToggle.test(lights[x][y])) {
                            cToggleTrue.accept(lights, x, y);
                        } else {
                            cToggleFalse.accept(lights, x, y);
                        }
                        break;
                }
            }
        }
    }
    
    // Helper methods

    private String getOp(String instruction) {
        return instruction.replaceAll(regex, "$1");
    }

    private int[] getStart(String instruction) {
        String start = instruction.replaceAll(regex, "$2");
        return new int[]{Integer.parseInt(start.split(",")[0]), Integer.parseInt(start.split(",")[1])};
    }

    private int[] getEnd(String instruction) {
        String end = instruction.replaceAll(regex, "$3");
        return new int[]{Integer.parseInt(end.split(",")[0]), Integer.parseInt(end.split(",")[1])};
    }

}
