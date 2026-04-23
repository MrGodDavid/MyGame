package david.game.input;

import bad.code.format.annotation.SingletonClass;
import com.mrgoddavid.vector.Vector2d;
import david.game.core.Game;
import david.game.core.Renderer;
import david.game.entity.EntityManager;

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
@SingletonClass
public final class InputManager {

    private static KeyboardListener keyboardListener;
    private static MouseInputListener mouseInputListener;

    private static InputManager instance;

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
        if (InputManager.instance == null) {
            InputManager.instance = new InputManager(keyboardListener, mouseInputListener);
        }
        return InputManager.instance;
    }

    /**
     * Update the game based on input from {@code KeyboardListener} and {@code MouseInputListener}.
     * <p>
     * <table>
     *     <caption>MyGame Input Map Table</caption>
     *     <tread>
     *         <tr><th>Key</th><th>Function</th></tr>
     *     </tread>
     *     <tbody>
     *         <tr><td>{@code ESCAPE} KEY</td><td>Exit the game.</td></tr>
     *         <tr><td>{@code W} KEY</td><td>Move the player.</td></tr>
     *         <tr><td>{@code X} KEY</td><td>Press once to pause the game, and press again to resume the game.</td></tr>
     *         <tr><td>{@code LEFT ARROW} KEY</td><td>Press once to switch to <strong><u>editor state</u></strong>,
     *         and press again to switch to  <strong><u>playing state</u></strong>.</td></tr>
     *         <tr><td>{@code F2} KEY</td><td>Press once to <strong><u>enable</u></strong> rendering game character's
     *         {@code collision box}, and press again to <strong><u>disable</u></strong> rendering game character's
     *         {@code collision box}.</td></tr>
     *     </tbody>
     *     <tread>
     *         <tr><th>Mouse Button</th><th>Function</th></tr>
     *     </tread>
     *     <tbody>
     *         <tr><td>{@code LEFT} BUTTON</td><td>Change player's direction in game.</td></tr>
     *     </tbody>
     * </table>
     */
    public void update() {
        // EXIT GAME
        if (keyboardListener.isKeyTyped(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        // MOVE PLAYER
        if (keyboardListener.isKeyPressed(KeyEvent.VK_W)) {
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
        if (keyboardListener.isKeyTyped(KeyEvent.VK_P)) {
            Game.toggleGamePauseResume();
        }
        // SWITCHING GAME STATES
        if (keyboardListener.isKeyTyped(KeyEvent.VK_LEFT)) {
            System.out.println("Switching state");
            Game.switchGameState();
            System.out.println("Current state: " + Game.getGameState());
        }
        // DEBUG
        // RENDER GAME CHARACTER'S COLLISION BOX
        if (keyboardListener.isKeyTyped(KeyEvent.VK_F2)) {
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
