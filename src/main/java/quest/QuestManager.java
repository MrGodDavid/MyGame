package quest;

import quest.objective.MoveTutorial;
import quest.objective.Objective;
import quest.objective.ShootTutorial;

/**
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public class QuestManager {

    private Objective currentObjective;

    public QuestManager(ObjectivePointer initialObjective) {
        transitionTo(initialObjective);
    }

    public void update() {
        if (currentObjective != null) {
            currentObjective.update();

            if (currentObjective.shouldTransition()) {
                transitionTo(currentObjective.getNextObjective());
            }
        }
    }

    private void transitionTo(ObjectivePointer objective) {
        currentObjective = switch (objective) {
            case ObjectivePointer.MOVE_TUTORIAL -> new MoveTutorial();
            case ObjectivePointer.SHOOT_TUTORIAL -> new ShootTutorial();
            case NULL -> null;
        };
    }

    public enum ObjectivePointer {
        MOVE_TUTORIAL,
        SHOOT_TUTORIAL,
        NULL;
    }

    @Override
    public String toString() {
        if (currentObjective == null)
            return "Current objective is null. Please check [transitionTo()] method of QuestManager class.";
        return currentObjective.toString();
    }

}
