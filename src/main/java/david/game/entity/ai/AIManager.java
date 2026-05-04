package david.game.entity.ai;

import david.game.entity.GameCharacter;
import david.game.entity.ai.state.AIState;
import david.game.entity.ai.state.Patrol;
import david.game.entity.ai.state.Wander;

/**
 * ===== [LinkedList] =====
 *
 * <p>
 * Here is a simple example of traversing a {@code LinkedList}.
 * </p>
 *
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
 *
 * <p>
 * The AIManager class traverses the AIState-AITransition linked list (AALL),
 * a linear data structure used in this game. The pointer from one node
 * ({@code AIState}) to another is predefined. {@code AITransition} provides
 * the pointer, and AIManager selects the next {@code AIState} based on it.
 * </p>
 *
 * <p>
 * Take a look at the AALL data structure. Here is a visualization:
 * </p>
 *
 * <pre>
 *                           [AITransition]          (pointer)
 *      e &lt;-------------------------------------------------------------------------- s
 *      v                                                                             ^
 * [AI State]  -----------&gt;  [AI State]  -----------&gt;  [AI State]  -----------&gt;  [AI State]
 *            [AITransition]            [AITransition]            [AITransition]
 *            (pointer)                 (pointer)                 (pointer)
 * </pre>
 *
 * <p>
 * Interestingly, the AIState-AITransition linked list (AALL) is actually a
 * {@code CircularlyLinkedList}. A CircularlyLinkedList is a special case of
 * a linked list where the last node points back to the first node, forming
 * a loop.
 * </p>
 *
 * <p>
 * Here is a simple algorithm for traversing a {@code CircularlyLinkedList}:
 * </p>
 *
 * <pre>
 * private CircularlyLinkedList list;
 *
 * // Assume that the list is correctly initialized in the constructor
 * // of this class.
 * public void example() {
 *      Node head = list.getHeadNode();
 *      Node traversePtr = list.getHeadNode();
 *      do {
 *          // do something while traversing the list
 *          traversePtr = traversePtr.getNext();
 *      } while (traversePtr != head);
 * }
 * </pre>
 *
 * <p>
 * ===== [Additional Notes] =====
 * </p>
 *
 * <p>
 * The AALL creates a new node whenever an {@code AITransition} condition is met,
 * pointing to the next {@code AIState}. This may not be the most memory-efficient
 * approach, but it is currently kept for simplicity.
 * </p>
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
