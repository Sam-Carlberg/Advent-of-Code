
package adventofcode.solutions;

/**
 * Variant on {@link java.util.function.Consumer} that takes three inputs rather
 * than one.
 *
 * @author Sam Carlberg
 */
interface TriConsumer<T, U, V> {

    /** No-op */
    public static final TriConsumer NONE = (t, u, v) -> {};

    public void accept(T t, U u, V v);

}
