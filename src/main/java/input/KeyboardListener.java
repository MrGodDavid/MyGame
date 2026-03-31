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

    private final boolean[] keys;

    public KeyboardListener() {
        this.keys = new boolean[256];
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
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }
}
