package utils;

import com.mrgoddavid.vector.Vector2d;
import core.Game;
import core.GameLoop;
import core.GameWindow;

import java.util.Random;

/**
 * Custom math class.
 *
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public final class Math {

    private Math() {
        throw new RuntimeException("You can't instantiate util.Math class.");
    }

    /**
     * Generate a random position for enemy to spawn in the game.
     * <p>
     * The range of x-component is defined as following
     * <pre><code>[-100, GameWindow.getWindowSize().getWidth() + 100]</code></pre>
     * <p>
     * The range of the y-component is defined as following:
     * <pre><code>[-100, 0] U [GameWindow.getWindowSize().getHeight() + 100]</code></pre>
     *
     * @return the random position, which represented by {@code Vector2d}, in x-component
     * range and y-component range.
     */
    public static Vector2d getRandomPosition() {
        final int OFFSET = 100;
        double randomX = Game.getRandomGenerator().nextDouble(-OFFSET, GameWindow.getWindowSize().getWidth() + OFFSET);
        double randomY = Game.getRandomGenerator().nextDouble() * OFFSET;
        randomY += Game.getRandomGenerator().nextBoolean() ? -OFFSET : GameWindow.getWindowSize().getHeight();
        return new Vector2d(randomX, randomY);
    }
}
