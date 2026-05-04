package david.game.utils;

import bad.code.exception.TimerNegativeDurationException;

/**
 * Custom timer class. This class is different from Java's built-in Timer class.
 * The constructor of this class contains the maximum time parameter of this {@code Timer}.
 *
 * <p>This timer is a countdown timer, meaning that the current time decrements every frame
 * until it reaches 0 in {@code update()}. The Timer class sets the current time to 0 if it
 * becomes less than or equal to 0.</p>
 *
 * <p>The {@code isFinished()} method checks whether the current time of {@code Timer}
 * is less than or equal to 0. The {@code reset()} method resets the current time to the
 * Timer's maximum time.</p>
 *
 * <p>The {@code toString()} method returns a string representation of this class,
 * formatted as follows:</p>
 *
 * <pre>
 * public String toString() {
 *     return "[TIMER]: currentTime=" + currentTime + ", maxTime=" + maxTime;
 * }
 * </pre>
 *
 * <p>
 * The {@code Timer} class does not provide accessor or mutator methods
 * for {@code currentTime} and {@code maxTime}.
 * </p>
 *
 * @author Mr. GodDavid
 * @since 4/3/2026
 */
public final class Timer {

    private final int maxTime;
    private int currentTime;

    /**
     * Construct this class by initialize the maxTime by the parameter of this constructor.
     *
     * @param maxTime maximum time of this timer.
     */
    public Timer(int maxTime) {
        if (maxTime < 0) {
            throw new TimerNegativeDurationException("[ERROR]:  maxTime < 0");
        }
        this.maxTime = maxTime;
        this.currentTime = this.maxTime;
    }

    /**
     * Update the timer by decrementing its currentTime integer field. If the currentTime is less than or equal
     * to 0, wrap the currentTime to 0.
     *
     * <pre><code>
     * public void update() {
     *     currentTime--;
     *     if (currentTime <= 0) {
     *         currentTime = 0;
     *     }
     * }
     * </code></pre>
     */
    public void update() {
        currentTime--;
        if (currentTime <= 0) {
            currentTime = 0;
        }
    }

    /**
     * Check if the timer is finished timing. The condition is checking if the currentTime field is equal to 0.
     *
     * @return true if {@code currentTime} equals to 0.
     */
    public boolean isFinished() {
        return currentTime == 0;
    }

    /**
     * Reset the {@code currentTime} field value back to its maximum time.
     */
    public void reset() {
        currentTime = maxTime;
    }

    /**
     * Print the string representation of this class. The string representation consists of the current time
     * and the maximum time of this timer.
     *
     * @return the string representation of this class.
     */
    @Override
    public String toString() {
        return "[TIMER]: currentTime=" + currentTime + ", maxTime=" + maxTime;
    }
}
