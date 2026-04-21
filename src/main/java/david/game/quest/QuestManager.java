package david.game.quest;

import david.game.entity.ai.AIManager;
import david.game.quest.objective.MoveTutorial;
import david.game.quest.objective.Objective;
import david.game.quest.objective.ShootTutorial;

/**
 * ===== [LinkedList] =====
 * Here is a simple code for traversing a LinkedList.
 * <pre><code>
 * private LinkedList list;
 * // Asume that the list is being correctly initialized in the constructor
 * // of this class.
 * public void example() {
 *      Node head = list.getHeadNode();
 *      Node traversePtr = head;
 *      while (traversePtr.getNext() != null) {
 *          // do something while traversing the list
 *          traversePtr = traversePtr.getNext();
 *      }
 * }
 * </code></pre>
 * <p>
 * QuestManager class traverses the Objective-ObjectiveTransition LinkedList (OOLL) that created in this game.
 * We once define this class once in Game class. The ObjectiveTransition provide pointer that reference
 * to the next Objective.
 * <p>
 * Take a look at AALL data structure. Here is a visualization:
 * <pre><code>
 *                           [ObjectiveTransition]          (pointer)
 *      e <------------------------------------------------------------------------------------- s
 *      v                                                                                        ^
 * [Objective]  ----------->  [Objective]  ----------->  [Objective]  ----------->  [Objective State]
 *            [ObjectiveTransition]            [ObjectiveTransition]            [ObjectiveTransition]
 *            (pointer)                        (pointer)                        (pointer)
 * </code></pre>
 *
 * @author Mr. GodDavid
 * @see AIManager AIManager class for more information about CircularlyLinkedList.
 * @since 4/10/2026
 */
public final class QuestManager {

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

    @SuppressWarnings("UnnecessarySemicolon")
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
