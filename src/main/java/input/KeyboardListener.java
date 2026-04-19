package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listen to user's keyboard input.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
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

    public boolean isKeyDown(int keyCode) {
        if (keys[keyCode] && !isPressed) {
            isPressed = true;
            return true;
        }
        return false;
    }
}
