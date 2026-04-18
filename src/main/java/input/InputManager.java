package input;

import com.mrgoddavid.vector.Vector2d;
import core.Game;
import core.Renderer;
import entity.EntityManager;

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

    public void update() {
        // EXIT GAME
        if (keyboardListener.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        // MOVE PLAYER
        if (keyboardListener.isKeyDown(KeyEvent.VK_W)) {
            EntityManager.getPlayer().move();
        } else {
            EntityManager.getPlayer().stop();
        }
        // CHANGE PLAYER'S MOVING DIRECTION
        if (InputManager.mouseInputListener.isButtonDown(MouseInputListener.MouseButton.LEFT_BUTTON)) {
            if (EntityManager.getPlayer().canShoot()) {
                EntityManager.getPlayer().shoot();
            }
        }
        // PAUSE GAME
        if (keyboardListener.isKeyDown(KeyEvent.VK_Z)) {
            Game.pause();
        }
        // RESUME GAME
        if (keyboardListener.isKeyDown(KeyEvent.VK_X)) {
            Game.resume();
        }
        // DEBUG
        // RENDER COLLISION BOX OF GAME CHARACTER
        if (keyboardListener.isKeyDown(KeyEvent.VK_F1)) {
            Renderer.setRenderCollisionBoxEnabled(true);
        }
        // DISABLE RENDERING COLLISION BOX OF GAME CHARACTER
        else if (keyboardListener.isKeyDown(KeyEvent.VK_F2)) {
            Renderer.setRenderCollisionBoxEnabled(false);
        }
    }

    public static void endFrame() {
        MouseInputListener.endFrame();
    }

    // =============================================== [GETTERS & SETTERS] ===============================================

    public static Vector2d getMousePosition() {
        return mouseInputListener.getMouseCursorPosition();
    }

    public static KeyboardListener getKeyboardListener() {
        return keyboardListener;
    }

    public static MouseInputListener getMouseInputListener() {
        return mouseInputListener;
    }
}
