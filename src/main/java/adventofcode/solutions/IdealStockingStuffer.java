
package adventofcode.solutions;

import adventofcode.framework.SolverClass;
import adventofcode.framework.SolverMethod;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.SneakyThrows;

/**
 * Solves the problems for day four of the advent calendar:
 * <a href="http://adventofcode.com/day/4">adventofcode.com/day/4</a>
 *
 * @author Sam Carlberg
 */
@SolverClass(day = 4)
public class IdealStockingStuffer {

    @SolverMethod(part = 1)
    public int lowestInt5(String secretKey) {
        return lowestInt(secretKey, 5);
    }

    @SolverMethod(part = 2)
    public int lowestInt6(String secretKey) {
        return lowestInt(secretKey, 6);
    }
    
    @SneakyThrows(NoSuchAlgorithmException.class)
    private int lowestInt(String secret, int numLeadingZeros) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String prefix = IntStream.range(0, numLeadingZeros)
                                 .mapToObj(i -> "0")
                                 .collect(Collectors.joining());
        return IntStream.iterate(0, i -> i + 1)
                        .filter(i -> bytesToHexString(md.digest((secret + i).getBytes())).startsWith(prefix))
                        .findFirst()
                        .orElse(-1);
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            sb.append(Integer.toHexString((b & 0xff) + 0x100).substring(1));
        }
        return sb.toString();
    }

}
