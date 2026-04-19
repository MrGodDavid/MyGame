package utils;

import bad.annotation.UninstantiableClass;
import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import core.Game;
import core.GameWindow;


/**
 * Custom math class.
 *
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
@UninstantiableClass
public final class Math {

    private Math() {
        throw new RuntimeException("You can't instantiate util.Math class.");
    }

    public static Vector2i toVector2i(Vector2d vector2d) {
        return new Vector2i((int) vector2d.getX(), (int) vector2d.getY());
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

    public static Vector2d getRandomPositionOnScreen() {
        double randomX = Game.getRandomGenerator().nextDouble() * GameWindow.getWindowSize().getWidth();
        double randomY = Game.getRandomGenerator().nextDouble() * GameWindow.getWindowSize().getHeight();
        return new Vector2d(randomX, randomY);
    }
}
