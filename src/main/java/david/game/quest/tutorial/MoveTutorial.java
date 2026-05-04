package david.game.quest.tutorial;

import david.game.input.InputManager;
import david.game.quest.ObjectiveTransition;
import david.game.quest.QuestManager;
import david.game.quest.objective.Objective;

import java.awt.event.KeyEvent;

/**
 * Move tutorial consist the player to press W key to move forward. However, the way that this game works
 * is that player needs to actively moving the mouse's cursor to change the direction of the player's game
 * character.
 * <p>
 * The MoveTutorial class is a subclass of {@link Objective}. This class has a private lambda function
 * {@link MoveTutorial#requirement()}, which implements the met condition of move tutorial.
 * <p>
 * The string representation of this class print this statement:
 * <pre><code>
 * "Current objective is move tutorial."
 * </code></pre>
 *
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public final class MoveTutorial extends Objective {

    public MoveTutorial() {
        super();
        title = "Move Tutorial";
        description = "Press [W] to \nmove,";
    }

    @Override
    protected ObjectiveTransition initializeTransition() {
        return new ObjectiveTransition(QuestManager.ObjectivePointer.SHOOT_TUTORIAL, this::requirement);
    }

    private boolean requirement() {
        return InputManager.isKeyPressed(KeyEvent.VK_W);
    }

    @Override
    public void update() {
        if (InputManager.isKeyPressed(KeyEvent.VK_W)) {
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        return "Current objective is move tutorial.";
    }
}
