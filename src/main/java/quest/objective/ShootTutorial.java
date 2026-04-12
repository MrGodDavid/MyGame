package quest.objective;

import input.InputManager;
import input.MouseInputListener;
import quest.ObjectiveTransition;
import quest.QuestManager;

/**
 * The shoot tutorial requires player to left-click on their mouse to shoot a projectile. There os no requirement
 * for the direction of shooting. The player can shoot projectiles if and only if pressing the left button of their
 * mouse, or click on the touchpad on their laptop.
 * <p>
 * The MoveTutorial class is a subclass of {@link Objective}. This class has a private lambda function
 * {@link ShootTutorial#requirement()}, which implements the met condition of move tutorial.
 * <p>
 * The string representation of this class print this statement:
 * <pre><code>
 * "Current objective is shoot tutorial."
 * </code></pre>
 *
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public final class ShootTutorial extends Objective {

    public ShootTutorial() {
        super();
    }

    @Override
    protected ObjectiveTransition initializeTransition() {
        return new ObjectiveTransition(QuestManager.ObjectivePointer.NULL, this::requirement);
    }

    private boolean requirement() {
        return InputManager.getMouseInputListener().isButtonDown(MouseInputListener.MouseButton.LEFT_BUTTON);
    }

    @Override
    public void update() {
    }

    @Override
    public String toString() {
        return "Current objective is shoot tutorial.";
    }
}
