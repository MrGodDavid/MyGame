package david.game.quest;

import david.game.core.Game;
import david.game.entity.ai.AIManager;
import david.game.graphics.UIManager;
import david.game.input.InputManager;
import david.game.quest.objective.CollectBattery;
import david.game.quest.objective.Objective;
import david.game.quest.tutorial.MoveTutorial;
import david.game.quest.tutorial.ShootTutorial;

import java.awt.event.KeyEvent;

/**
 * ===== [LinkedList] =====
 * <p>Here is a simple code for traversing a LinkedList.</p>
 * <pre>
 * private LinkedList list;
 * // Assume that the list is correctly initialized in the constructor
 * // of this class.
 * public void example() {
 *      Node head = list.getHeadNode();
 *      Node traversePtr = head;
 *      while (traversePtr.getNext() != null) {
 *          // do something while traversing the list
 *          traversePtr = traversePtr.getNext();
 *      }
 * }
 * </pre>
 * <p>
 * QuestManager class traverses the Objective-ObjectiveTransition LinkedList (OOLL) created in this game.
 * This class is defined once in the Game class. The ObjectiveTransition provides a pointer
 * that references the next Objective.
 * </p>
 * <p>
 * Take a look at AALL data structure. Here is a visualization:
 * </p>
 * <pre>
 *                           [ObjectiveTransition]          (pointer)
 *      e &lt;------------------------------------------------------------------------------------- s
 *      v                                                                                        ^
 * [Objective]  -----------&gt;  [Objective]  -----------&gt;  [Objective]  -----------&gt;  [Objective State]
 *            [ObjectiveTransition]            [ObjectiveTransition]            [ObjectiveTransition]
 *            (pointer)                        (pointer)                        (pointer)
 * </pre>
 *
 * @author Mr. GodDavid
 * @see AIManager
 * @since 4/10/2026
 */
public final class QuestManager {

    private Objective currentObjective;

    @SuppressWarnings("UnnecessarySemicolon")
    public enum ObjectivePointer {
        MOVE_TUTORIAL,
        SHOOT_TUTORIAL,
        COLLECT_FUEL_OBJECTIVE,
        COLLECT_BATTERY_OBJECTIVE,
        NULL;
    }

    public QuestManager(ObjectivePointer initialObjective) {
        transitionTo(initialObjective);
    }

    public void update() {
        if (currentObjective != null) {
            currentObjective.update();
            UIManager.updateObjectivePanel();

            if (currentObjective.shouldTransition()) {
                transitionTo(currentObjective.getNextObjective());
            }
        }

        if (Game.isDebugMode()) {
            if (InputManager.isKeyDown(KeyEvent.VK_F10)) {
                advance();
            }
        }
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    private void transitionTo(ObjectivePointer objective) {
        currentObjective = switch (objective) {
            case ObjectivePointer.MOVE_TUTORIAL -> new MoveTutorial();
            case ObjectivePointer.SHOOT_TUTORIAL -> new ShootTutorial();
            case ObjectivePointer.COLLECT_BATTERY_OBJECTIVE -> new CollectBattery();
            case ObjectivePointer.NULL -> null;
            default -> null;
        };
    }

    public void advance() {
        if (currentObjective.getNextObjective() == ObjectivePointer.NULL) {
            transitionTo(ObjectivePointer.MOVE_TUTORIAL);
            return;
        }
        transitionTo(currentObjective.getNextObjective());
    }

    public Objective getCurrentObjective() {
        return currentObjective;
    }

    @Override
    public String toString() {
        if (currentObjective == null)
            return "Current objective is null. Please check [transitionTo()] method of QuestManager class.";
        return currentObjective.toString();
    }

}
