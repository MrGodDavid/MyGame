package quest;

import entity.GameCharacter;

/**
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public class ObjectiveTransition {

    private final QuestManager.ObjectivePointer nextState;
    private final Requirement requirement;

    public ObjectiveTransition(QuestManager.ObjectivePointer nextObjective, Requirement requirement) {
        this.nextState = nextObjective;
        this.requirement = requirement;
    }

    public boolean shouldTransition() {
        return requirement.isMet();
    }

    public QuestManager.ObjectivePointer getNextState() {
        return nextState;
    }
}
