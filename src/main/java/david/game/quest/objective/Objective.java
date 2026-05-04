package david.game.quest.objective;

import david.game.quest.ObjectiveTransition;
import david.game.quest.QuestManager;

/**
 * The Objective class is the superclass for all game objectives or quests. This class contains a private
 * {@code ObjectiveTransition}, which acts the pointer class in Objective-ObjectiveTransition LinkedList (OOLL)
 * linear data structure.
 * <p>
 * The constructor of this class constructs the {@code ObjectiveTransition} pointer class by calling the
 * {@link Objective#initializeTransition()} method, an {@link Objective#update()}, a condition checker,
 * or {@link Objective#shouldTransition()}, and an accessosr method of the next Objective in OOLL.
 *
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public abstract class Objective {

    private final ObjectiveTransition transition;

    protected String title;
    protected String description;

    protected boolean isFinished;

    public Objective() {
        title = "";
        description = "";
        isFinished = false;
        transition = initializeTransition();
    }

    /**
     * Initializes the ObjectiveTransition pointer class of a specific subclass of {@code Objective}.
     *
     * @return the constructed ObjectiveTransition, which is the pointer class, in OOLL.
     */
    protected abstract ObjectiveTransition initializeTransition();

    /**
     * Updates the Objective in game loop. The update could be updating the number of kills of enemy in
     * EnemyKillingObjective.
     */
    public abstract void update();

    /**
     * Check if the current objective is finished. Finishing conditions are specified in child classes.
     *
     * @return true if all conditions of this objective are met.
     * @apiNote This method DOES NOT define the transitioning condition.
     */
    public abstract boolean isFinished();

    /**
     * Wrapper method of {@link ObjectiveTransition#shouldTransition()}.
     *
     * @return true if all requirements of current objective are met.
     */
    public boolean shouldTransition() {
        return transition.shouldTransition();
    }

    /**
     * Accessor of the pointer class of the subclass of Objective.
     *
     * @return the pointer class of the subclass of Objective.
     */
    public QuestManager.ObjectivePointer getNextObjective() {
        return transition.getNextObjective();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
