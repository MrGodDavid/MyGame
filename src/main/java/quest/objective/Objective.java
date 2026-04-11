package quest.objective;

import quest.ObjectiveTransition;
import quest.QuestManager;

/**
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public abstract class Objective {

    private final ObjectiveTransition transition;

    public Objective() {
        transition = initializeTransition();
    }

    protected abstract ObjectiveTransition initializeTransition();

    public abstract void update();

    public boolean shouldTransition() {
        return transition.shouldTransition();
    }

    public QuestManager.ObjectivePointer getNextObjective() {
        return transition.getNextState();
    }
}
