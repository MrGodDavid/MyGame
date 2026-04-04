package utils;

/**
 * Custom timer class. This class is different from Java's built Timer class. The constructor of this class contain
 * the maximum time parameter of this {@code Timer}. This timer is a countdown timer. Meaning that the current time
 * decrements every frame until it reaches 0 in {@code update()}. The Timer class sets the current time to 0 if it
 * is less than or equal to 0. The {@code isFinished()} method checks if the current time of {@code Timer} is below
 * or equal to 0. The {@code reset()} resets the current time to Timer's maximum time. The {@code toString()} method
 * returns a String representation of this class, which consists with like the following:
 * <code><pre>
 * public String toString() {
 *     return "[TIMER]: currentTime=" + currentTime + ", maxTime=" + maxTime;
 * }
 * </pre></code>
 * <p>
 * The {@code Timer} class does not contain accessor or mutator methods for {@code currentTime} and {@code maxTime}.
 *
 * @author Mr. GodDavid
 * @since 4/3/2026
 */
public final class Timer {

    private final int maxTime;
    private int currentTime;

    public Timer(int maxTime) {
        this.maxTime = maxTime;
        this.currentTime = this.maxTime;
    }

    public void update() {
        currentTime--;
        if (currentTime <= 0) {
            currentTime = 0;
        }
    }

    public boolean isFinished() {
        return currentTime <= 0;
    }

    public void reset() {
        currentTime = maxTime;
    }

    @Override
    public String toString() {
        return "[TIMER]: currentTime=" + currentTime + ", maxTime=" + maxTime;
    }
}
