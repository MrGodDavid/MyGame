package input;

import bad.annotation.SingletonClass;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listen to user's keyboard input.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
@SingletonClass
public final class KeyboardListener implements KeyListener {

    private static KeyboardListener instance;

    private final boolean[] keys;
    private boolean isPressed;

    private KeyboardListener() {
        this.keys = new boolean[256];
        this.isPressed = false;
    }

    public static KeyboardListener getInstance() {
        if (instance == null) {
            instance = new KeyboardListener();
        }
        return instance;
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys[keyCode] = true;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys[keyCode] = false;
        isPressed = false;
    }

    /**
     * Fire a key event <strong>ONLY ONE TIME</strong> in game loop. If the player presses down a key, the key event
     * would be fired one time.
     * <p>
     * For pressing down a key that fires multiple events in game loop, use {@link KeyboardListener#isKeyPressed(int)}.
     *
     * @param keyCode represented by an integer value that indicate which key on user's keyboard typed.
     * @return a boolean that indicate whether the key event been fired. True represents the user presses down a certain
     * key, and false represents the user does not press a certain key.
     */
    public boolean isKeyTyped(int keyCode) {
        if (keys[keyCode] && !isPressed) {
            isPressed = true;
            return true;
        }
        return false;
    }

    /**
     * Fire a key event <strong>MULTIPLE TIMES</strong> in game loop. If the player types a key (presses down and
     * releases the key quickly), the key event still would be fired multiple times.
     * <p>
     * For typing down a key and fire only ONE TIME, use {@link KeyboardListener#isKeyTyped(int)}.
     *
     * @param keyCode represented by an integer value that indicate which key on user's keyboard typed.
     * @return a boolean that indicate whether the key event been fired. True represents the user presses down a certain
     * key, and false represents the user does not press a certain key.
     */
    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }
}
