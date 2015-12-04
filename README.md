# Advent of Code
Java solutions for the 2015 [Advent of Code](http://adventofcode.com/)

## Building

Command line (Windows, OS X, and Linux)  
1. Clone the repository and cd into it   
2. Run `gradle build` (Windows) or `./gradlew build` (OS X, Linux)  
3. Build artifacts are placed in `build/`; the jar will be at `build/libs/AdventOfCode.jar`


## Running

Running `java -jar AdventOfCode.jar` will run all solutions in order, starting from day 1 and ending
with the last day that a solution has been written for.
(There's no guarantee that this will always be up to date while the advent is ongoing)

You can specify which solutions to run from the command line. For example, if you only want to run
the solutions for days 1, 3, and 6, enter `java -jar AdventOfCode.jar 1 3 6`.
These will still run in order, regardless of the order of the arguments
(e.g. `java -jar AdventOfCode 3 2 1` will run the solution for day 1, then the solution for day 2, then the one for day 3)
