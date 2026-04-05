package input;

import com.mrgoddavid.vector.Vector2d;
import entity.MovingEntityManager;
import entity.enemy.Enemy;

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

    private static KeyboardListener keyboardListener;
    private static MouseInputListener mouseInputListener;

    private static InputManager inputManager;

    private InputManager(KeyboardListener keyboardListener, MouseInputListener mouseInputListener) {
        InputManager.keyboardListener = keyboardListener;
        InputManager.mouseInputListener = mouseInputListener;
    }

    /**
     * Returns the single instance of {@code InputManager}. This method initializes the {@code InputManager} if the
     * instance is not being initialized. Otherwise, returns the instance of {@code InputManager}.
     *
     * @param keyboardListener the key listener that is used to construct this class.
     * @param mouseInputListener the mouse input listener that is used to construct this class.
     * @return the only instance of {@code InputManager}
     */
    public static InputManager getInstance(KeyboardListener keyboardListener, MouseInputListener mouseInputListener) {
        if (InputManager.inputManager == null) {
            InputManager.inputManager = new InputManager(keyboardListener, mouseInputListener);
        }
        return InputManager.inputManager;
    }

    public void update(double deltaTime) {
        if (keyboardListener.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }

        if (keyboardListener.isKeyDown(KeyEvent.VK_W)) {
            MovingEntityManager.getPlayer().move(deltaTime);
        } else {
            MovingEntityManager.getPlayer().stop();
        }

        if (InputManager.mouseInputListener.isButtonDown(MouseInputListener.MouseButton.LEFT_BUTTON)) {
            MovingEntityManager.getPlayer().shoot();
        }
    }

    public static void endFrame() {
        MouseInputListener.endFrame();
    }

    // =============================================== [GETTERS & SETTERS] ===============================================

    public static Vector2d getMousePosition() {
        return mouseInputListener.getMouseCursorPosition();
    }
}
