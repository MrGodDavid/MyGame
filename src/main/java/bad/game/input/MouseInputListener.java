package bad.game.input;

import bad.game.annotation.SingletonClass;
import com.mrgoddavid.vector.Vector2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

/**
 * Listen to user's mouse inputs.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
@SingletonClass
public final class MouseInputListener implements MouseListener, MouseMotionListener {

    private static MouseInputListener instance;

    public enum MouseButton {

        LEFT_BUTTON(MouseEvent.BUTTON1),
        MIDDLE_BUTTON(MouseEvent.BUTTON2),
        RIGHT_BUTTON(MouseEvent.BUTTON3),
        SIDE_BUTTON_CLOSER_TO_USER(4),
        SIDE_BUTTON_AWAY_FROM_USER(5);

        private final int button;

        MouseButton(int button) {
            this.button = button;
        }

        public int getButton() {
            return button;
        }
    }

    private static boolean[] mouseButtonsPressed;
    private Vector2d mouseCursorPosition;

    private MouseInputListener() {
        mouseButtonsPressed = new boolean[MouseInputListener.MouseButton.values().length];
        this.mouseCursorPosition = new Vector2d(-1, -1);
    }

    public static MouseInputListener getInstance() {
        if (instance == null) {
            instance = new MouseInputListener();
        }
        return instance;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        /*
         *  left button -> 1
         *  middle button -> 2
         *  right button -> 3
         *  side button (closer to user's body) -> 4
         *  side button (away from user's body) -> 5
         */
        int mouseButtonIndex = e.getButton() - 1;
        if (mouseButtonIndex >= 0 && mouseButtonIndex < mouseButtonsPressed.length) {
            mouseButtonsPressed[mouseButtonIndex] = true;
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        /*
         *  left button -> 1
         *  middle button -> 2
         *  right button -> 3
         *  side button (closer to user's body) -> 4
         *  side button (away from user's body) -> 5
         */
        int mouseButtonIndex = e.getButton() - 1;
        if (mouseButtonIndex >= 0 && mouseButtonIndex < mouseButtonsPressed.length) {
            mouseButtonsPressed[mouseButtonIndex] = false;
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseCursorPosition = new Vector2d(e.getX(), e.getY());
    }

    /**
     * Getter for {@code mouseCursorPosition}
     */
    public Vector2d getMouseCursorPosition() {
        return mouseCursorPosition;
    }

    public boolean isButtonDown(MouseButton button) {
        if (button.getButton() < 0 || button.getButton() >= mouseButtonsPressed.length) return false;
        return mouseButtonsPressed[button.getButton() - 1];
    }

    public static void endFrame() {
        Arrays.fill(mouseButtonsPressed, false);
    }
}
