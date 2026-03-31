package input;

import java.awt.event.KeyEvent;

/**
 * Manages user's keyboard and mouse inputs. First constructs this class with {@code KeyboardListener}
 * and {@code MouseInputListener}. Utilize the {@code update(double)} method to update user's input.
 * @apiNote Process input logics in this class. <b>DO NOT IMPLEMENT ANY LOGICS IN EITHER {@code KeyboardListener}
 * or {@code MouseInputListener}.</b>
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
public final class InputManager {

    private final KeyboardListener keyboardListener;
    private final MouseInputListener mouseInputListener;

    public InputManager(KeyboardListener keyboardListener, MouseInputListener mouseInputListener) {
        this.keyboardListener = keyboardListener;
        this.mouseInputListener = mouseInputListener;
    }

    public void update(double deltaTime) {
        if (keyboardListener.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }

    }
}
