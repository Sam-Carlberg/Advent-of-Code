
package adventofcode;

import adventofcode.framework.SolverClass;
import adventofcode.framework.SolverMethod;
import adventofcode.util.Reflection;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Method;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static java.util.stream.Collectors.toList;
import lombok.SneakyThrows;

/**
 * Main class. This will automatically run all classes in this project that  are
 * annotated with {@link SolverClass @SolverClass}
 *
 * @author Sam Carlberg
 */
public class Runner {

    /**
     * Runs solutions for the challenges in the 2015 advent of code.
     * 
     * @param args command-line arguments. These should be the numbers of the
     * days to run solutions for (e.g. {@code java -jar AdventOfCode.jar 1 4 6}.
     * If no arguments are given, all solutions will be run sequentially.
     */
    public static void main(String[] args) {
        // Print warning for invalid arguments
        Stream.of(args)
              .filter(s -> !s.matches("[0-9]+"))
              .forEach(s -> System.out.printf("Invalid argument '%s' (arguments must be positive integer values)\n", s));

        // Map the strings to distinct integers
        List<Integer> daysToRun = Stream.of(args)
                                        .filter(s -> s.matches("[0-9]+"))
                                        .mapToInt(Integer::parseInt)
                                        .distinct()
                                        .boxed()
                                        .collect(toList());
        getSolvers().stream()
                    .filter(c -> daysToRun.isEmpty() || daysToRun.contains(getDay(c)))
                    .sorted((c1, c2) -> getDay(c1) - getDay(c2))
                    .map(c -> Reflection.instantiate(c))
                    .forEachOrdered(solver -> run(solver));
    }
    
    public static void run(Object solver) {
        Class<?> clazz = solver.getClass();
        if(!clazz.isAnnotationPresent(SolverClass.class)) {
            System.out.println("Not a solver: " + solver);
            return;
        }
        Stream.of(clazz.getMethods())
              .filter(m -> m.isAnnotationPresent(SolverMethod.class))
              .filter(m -> Arrays.equals(m.getParameterTypes(), new Class[]{String.class}))
              .sorted((m1, m2) -> m1.getAnnotation(SolverMethod.class).part() - m2.getAnnotation(SolverMethod.class).part())
              .forEachOrdered(m -> runSolverMethod(m, solver));
    }
    
    /**
     * Gets the day the given subclass of Solver finds the solutions for.
     */
    public static int getDay(Class<?> clazz) {
        return clazz.getAnnotation(SolverClass.class).day();
    }

    /**
     * Gets all solver classes (assumes all classes are in one project)
     */
    @SneakyThrows(ClassNotFoundException.class)
    public static List<Class<?>> getSolvers() {
        List<Class<?>> solvers = new ArrayList<>();
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(getJarFile()))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (!fileName.endsWith(".class")) {
                    continue;
                }
                String className = fileName.replaceAll("/", ".").replace(".class", "");
                if (!className.startsWith("adventofcode")) {
                    continue;
                }
                Class<?> clazz = Class.forName(className);
                if (Reflection.hasDefaultConstructor(clazz)
                        && clazz.isAnnotationPresent(SolverClass.class)) {
                    solvers.add(clazz);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not find classes, exiting");
            System.exit(1);
        }
        return solvers;
    }

    /**
     * Gets the jar file running this main class (i.e. AdventOfCode.jar)
     */
    @SneakyThrows(UnsupportedEncodingException.class)
    public static File getJarFile() {
        String path = Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        return new File(decodedPath);
    }
    
    private static void runSolverMethod(Method m, Object instance) {
        int day = getDay(instance.getClass());
        int part = m.getAnnotation(SolverMethod.class).part();
        Object result = Reflection.invoke(instance, m, getInputStringForDay(day));
        System.out.printf("Solution for day %d, part %d: %s\n", day, part, result);
    }
    
    private static String getInputStringForDay(int day) {
        final String filePattern = "/inputs/input_day%d.txt";
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(Runner.class.getResourceAsStream(String.format(filePattern, day))))) {
            return r.lines().collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't open input file for day " + day, ex);
        }
    }

}
