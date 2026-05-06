package david.game.input;

import bad.code.format.annotation.SingletonClass;
import com.mrgoddavid.vector.Vector2d;
import david.game.core.Game;
import david.game.core.Renderer;
import david.game.entity.EntityManager;
import david.game.graphics.UIManager;

import java.awt.event.KeyEvent;

/**
 * Manages the user's keyboard and mouse input.
 * Construct this class by providing {@code KeyboardListener}
 * and {@code MouseInputListener}.
 *
 * <p>
 * Use the {@code update(double)} method to process and update user input each frame.
 * </p>
 *
 * <p>
 * Process all input logic within this class. Do not implement any logic inside {@code KeyboardListener}
 * or {@code MouseInputListener}.
 * </p>
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
@SingletonClass
@SuppressWarnings("unused")
public final class InputManager {

    @SuppressWarnings("unused")
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
        // PLAYING STATE
        if (Game.getGameState() == Game.GameState.PLAYING_STATE || Game.getGameState() == Game.GameState.DEBUG_STATE) {
            // MOVE PLAYER
            if (KeyboardListener.isKeyPressed(KeyEvent.VK_W)) {
                // SPRINT
                if (KeyboardListener.isKeyPressed(KeyEvent.VK_TAB)) {
                    EntityManager.getPlayer().sprint();
                } else {
                    EntityManager.getPlayer().resetSpeed();
                }
                // CROUCH
                if (KeyboardListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
                    EntityManager.getPlayer().crouch();
                } else {
                    EntityManager.getPlayer().resetSpeed();
                }
                // NORMAL MOVING PLAYER
                EntityManager.getPlayer().move();
            } else {
                EntityManager.getPlayer().stop();
            }
            // CHANGE PLAYER'S MOVING DIRECTION
            if (InputManager.isButtonDown(MouseInputListener.MouseButton.LEFT_BUTTON)) {
                if (EntityManager.getPlayer().canShoot()) {
                    EntityManager.getPlayer().shoot();
                }
            }
        }
        // EXIT GAME
        if (KeyboardListener.isKeyTyped(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        // PAUSE/RESUME GAME
        if (KeyboardListener.isKeyTyped(KeyEvent.VK_P)) {
            Game.toggleGamePauseResume();
        }
        // SWITCHING GAME STATES
        if (KeyboardListener.isKeyTyped(KeyEvent.VK_LEFT)) {
//            Game.switchGameState();

        }
        // DEBUG
        // DRAW THE BOUNDING BOX OF EACH UI COMPONENT
        if (KeyboardListener.isKeyTyped(KeyEvent.VK_F1)) {
            UIManager.toggleRenderUIComponentBoundingBox();
        }
        // RENDER GAME CHARACTER'S COLLISION BOX
        if (KeyboardListener.isKeyTyped(KeyEvent.VK_F2)) {
            Renderer.toggleRenderCollisionBox();
        }
        // ENABLE DEBUG MODE
        if (KeyboardListener.isKeyTyped(KeyEvent.VK_F3)) {
            Game.toggleDebugMode();
        }
    }

    public static boolean isMouseMoved() {
        return MouseInputListener.isMouseMoved();
    }

    /**
     * Wrapper method of {@link KeyboardListener#isKeyTyped(int)}.
     *
     * @param keyCode that indicates which key is typed on keyboard.
     * @return true if the correspond key is typed.
     */
    public static boolean isKeyDown(int keyCode) {
        return KeyboardListener.isKeyTyped(keyCode);
    }

    /**
     * Wrapper method of {@link KeyboardListener#isKeyPressed(int)}.
     *
     * @param keyCode that indicates which key is pressed on keyboard.
     * @return true if the correspond key is pressed.
     */
    public static boolean isKeyPressed(int keyCode) {
        return KeyboardListener.isKeyPressed(keyCode);
    }

    /**
     * Wrapper method of {@link MouseInputListener#isButtonDown(MouseInputListener.MouseButton)}.
     *
     * @param button that indicates which mouse button is pressed on mouse.
     * @return true if the correspond mouse button is pressed.
     */
    public static boolean isButtonDown(MouseInputListener.MouseButton button) {
        return MouseInputListener.isButtonDown(button);
    }

    public static boolean isButtonPressed(MouseInputListener.MouseButton button) {
        return MouseInputListener.isButtonPressed(button);
    }

    // =============================================== [GETTERS & SETTERS] ===============================================

    public static Vector2d getMousePosition() {
        return mouseInputListener.getMouseCursorPosition();
    }
}
