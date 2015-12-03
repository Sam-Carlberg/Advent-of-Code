
package adventofcode;

import adventofcode.framework.Solver;
import adventofcode.framework.SolverClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Modifier;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static java.util.stream.Collectors.toList;

/**
 * Main class. This will automatically run all classes in this project that
 * directly subclass {@link Solver} and are annotated with {@link SolverClass @SolverClass}
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
                    .map(c -> instantiate(c))
                    .forEachOrdered(Solver::solve);
    }
    
    /**
     * Gets the day the given subclass of Solver finds the solutions for.
     */
    private static int getDay(Class<? extends Solver> clazz) {
        return clazz.getAnnotation(SolverClass.class).day();
    }

    /**
     * Gets all solver classes (assumes all classes are in one project)
     */
    private static List<Class<? extends Solver>> getSolvers() {
        List<Class<? extends Solver>> solvers = new ArrayList<>();
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(getJarFile()))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (!fileName.endsWith(".class")) {
                    continue;
                }
                String className = fileName.replaceAll("/", ".").replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    if (hasDefaultConstructor(clazz)
                            && Solver.class.equals(clazz.getSuperclass())
                            && clazz.isAnnotationPresent(SolverClass.class)) {
                        solvers.add((Class<? extends Solver>) clazz);
                    }
                } catch (ClassNotFoundException ex) {
                    // I'd normally use @lombok.SneakyThrows for this, 
                    // but I don't want to use any external libraries
                }
            }
        } catch (IOException e) {
            System.out.println("Could not find classes, exiting");
            System.exit(1);
            return null;
        }
        return solvers;
    }

    /**
     * Gets the jar file running this main class (i.e. AdventOfCode.jar)
     */
    private static File getJarFile() {
        try {
            String path = Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            return new File(decodedPath);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("The end has come: UTF-8 is no longer a supported string encoding", ex);
        }
    }
    
    /**
     * Checks if the given class has a default constructor.
     */
    private static boolean hasDefaultConstructor(Class<?> clazz) {
        try {
            return (clazz.getConstructor().getModifiers() & Modifier.PUBLIC) > 0;
        } catch (NoSuchMethodException | SecurityException e) {
            return false;
        }
    }

    /**
     * Instantiates the given class. Used to simplify stream operations.
     */
    private static <T> T instantiate(Class<T> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("No default constructor in " + clazz, ex);
        }
    }

}
