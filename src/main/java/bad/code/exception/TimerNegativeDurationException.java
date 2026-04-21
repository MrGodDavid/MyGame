package bad.code.exception;

/**
 * Throws this exception when user inputting a negative value to the parameter of the constructor of {@code Timer}.
 * A {@code Timer} cannot hold a negative duration since time cannot be negative.
 *
 * @author Mr. GodDavid
 * @since 4/20/2026
 */
public class TimerNegativeDurationException extends RuntimeException {

    public TimerNegativeDurationException(String message) {
        super(message);
    }
}
