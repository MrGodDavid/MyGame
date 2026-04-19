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
     * @param keyboardListener   the key listener that is used to construct this class.
     * @param mouseInputListener the mouse input listener that is used to construct this class.
     * @return the only instance of {@code InputManager}
     */
    public static InputManager getInstance(KeyboardListener keyboardListener, MouseInputListener mouseInputListener) {
        if (InputManager.inputManager == null) {
            InputManager.inputManager = new InputManager(keyboardListener, mouseInputListener);
        }
        return InputManager.inputManager;
    }

    /**
     * Update the game based on input from {@code KeyboardListener} and {@code MouseInputListener}.
     * <p>
     * Here is a chart of keymap of this game:
     * <p>
     * =========================================
     * <ul>
     *  <li>{@code ESCAPE KEY} -> Exit the game.</li>
     *  <li>{@code W KEY} -> Move the player.</li>
     *  <li>{@code LEFT MOUSE BUTTON} -> Change the direction of player.</li>
     *  <li>{@code X KEY} -> Press once pause the game, and press again to resume the game.</li>
     *  <li>{@code LEFT ARROW KEY} -> Press once to switch to <strong><u>editor state</u></strong>, and press again
     *      to switch to  <strong><u>playing state</u></strong>. </li>
     *  <li>{@code F2 KEY} -> Press once to <strong><u>enable</u></strong> rendering game character's
     *  {@code collision box}, and press again to <strong><u>disable</u></strong> rendering game character's
     *  {@code collision box}.</li>
     * </ul>
     * =========================================
     */
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
        // PAUSE/RESUME GAME
        if (keyboardListener.isKeyDown(KeyEvent.VK_X)) {
            Game.toggleGamePauseResume();
        }
        // SWITCHING GAME STATES
        if (keyboardListener.isKeyDown(KeyEvent.VK_LEFT)) {
            System.out.println("Switching state");
        }
        // DEBUG
        // RENDER GAME CHARACTER'S COLLISION BOX
        if (keyboardListener.isKeyDown(KeyEvent.VK_F2)) {
            Renderer.toggleRenderCollisionBox();
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
