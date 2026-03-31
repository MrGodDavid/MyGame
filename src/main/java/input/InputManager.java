package input;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import game.Game;

import java.awt.event.KeyEvent;

/**
 * Manages user's keyboard and mouse inputs. First constructs this class with {@code KeyboardListener}
 * and {@code MouseInputListener}. Utilize the {@code update(double)} method to update user's input.
 *
 * @author Mr. GodDavid
 * @apiNote Process input logics in this class. <b>DO NOT IMPLEMENT ANY LOGICS IN EITHER {@code KeyboardListener}
 * or {@code MouseInputListener}.</b>
 * @since 3/31/2026
 */
public final class InputManager {

    private final KeyboardListener keyboardListener;
    private static MouseInputListener mouseInputListener;

    public InputManager(KeyboardListener keyboardListener, MouseInputListener mouseInputListener) {
        this.keyboardListener = keyboardListener;
        InputManager.mouseInputListener = mouseInputListener;
    }

    public void update(double deltaTime) {
        if (keyboardListener.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }

        if (keyboardListener.isKeyDown(KeyEvent.VK_W)) {
            Game.getPlayer().move(deltaTime);
        } else {
            Game.getPlayer().stop();
        }
    }

    public static Vector2d getMousePosition() {
        return mouseInputListener.getMouseCursorPosition();
    }
}
