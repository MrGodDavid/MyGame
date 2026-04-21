package david.game.entity.ai;

import david.game.entity.GameCharacter;
import david.game.entity.ai.state.AIState;
import david.game.entity.ai.state.Patrol;
import david.game.entity.ai.state.Wander;

/**
 * ===== [LinkedList] =====
 * <p>
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
 * AIManager class traverses the AIState-AITransition_linkedList (AALL) linear data structure that created in this game.
 * The pointer from node (AIState) to other node (AIState) is pre-defined in class. The AITransition provide the pointer,
 * and AIManager select the node (AIState) that matches with the pointer.
 * <p>
 * Take a look at AALL data structure. Here is a visualization:
 * <pre><code>
 *                           [AITransition]          (pointer)
 *      e <-------------------------------------------------------------------------- s
 *      v                                                                             ^
 * [AI State]  ----------->  [AI State]  ----------->  [AI State]  ----------->  [AI State]
 *            [AITransition]            [AITransition]            [AITransition]
 *            (pointer)                 (pointer)                 (pointer)
 * </code></pre>
 * Surprisingly, the AIState-AITransition LinkedList (AALL) is actually a {@code CircularlyLinkedList}.
 * A CircularlyLinkedList is a special case of LinkedList, where the last node contains a pointer to the reference
 * of the first node, creating an infinite loop if traversing through this list.
 * <p>
 * Here is a simple algorithm for traversing the CircularlyLinkedList.
 * <pre><code>
 * private CircularlyLinkedList list;
 *
 * // Asume that the list is being correctly initialized in the constructor
 * // of this class.
 * public void example() {
 *      Node head = list.getHeadNode();
 *      Node traversePtr = list.getHeadNode();
 *      do {
 *          // do something while traversing the list.
 *          // ...
 *          traversePtr = traversePtr.getNext();
 *      } while (traversePtr != head);
 * }
 * </code></pre>
 * <p>
 * ===== [Additional Notes] =====
 * <p>
 * The AALL creates a new node whenever the AITransition pointer met its conditions and points to the next AIState
 * (node). Unfortunately, I could not find a better way to save memory. Right now, let's keep it this way. :)
 *
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public final class AIManager {

    private AIState currentState;

    public AIManager(AIStatePointer initialState) {
        transitionTo(initialState);
    }

    public void update(GameCharacter character) {
        if (currentState == null) {
            System.out.println("[WARNING]: AI state is null");
            return;
        }
        currentState.update(character);

        if (currentState.shouldTransition(character)) {
            transitionTo(currentState.getNextState());
        }
    }

    private void transitionTo(AIStatePointer nextState) {
        currentState = switch (nextState) {
            case WANDER -> new Wander();
            case PATROL -> new Patrol();
            default -> null;
        };
    }

    @SuppressWarnings("UnnecessarySemicolon")
    public enum AIStatePointer {
        WANDER,
        PATROL,
        NULL;
    }

    @Override
    public String toString() {
        if (currentState == null)
            return "Current state is null. Please check [transitionTo()] method of AIManager class.";
        return currentState.toString();
    }
}
