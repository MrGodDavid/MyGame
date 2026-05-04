package david.game.quest.tutorial;

import david.game.entity.player.Player;
import david.game.input.InputManager;
import david.game.input.MouseInputListener;
import david.game.quest.ObjectiveTransition;
import david.game.quest.QuestManager;
import david.game.quest.objective.Objective;

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
        title = "Shoot Tutorial";
        description = "Press \n[LEFT BUTTON] \nto shoot.";
    }

    @Override
    protected ObjectiveTransition initializeTransition() {
        return new ObjectiveTransition(QuestManager.ObjectivePointer.COLLECT_BATTERY_OBJECTIVE, this::requirement);
    }

    private boolean requirement() {
        return InputManager.isButtonPressed(MouseInputListener.MouseButton.LEFT_BUTTON) && !Player.getInstance().canShoot();
    }

    @Override
    public void update() {
        if (InputManager.isButtonPressed(MouseInputListener.MouseButton.LEFT_BUTTON)) {
            isFinished = true;
        }
    }

    /**
     * Check if the current objective is finished. Finishing conditions are specified in child classes.
     *
     * @return true if all conditions of this objective are met.
     * @apiNote This method DOES NOT define the transitioning condition.
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        return "Current objective is shoot tutorial.";
    }
}
