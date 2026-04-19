package bad.game.entity.ai;

import bad.game.entity.ai.state.AIState;
import bad.game.entity.GameCharacter;

/**
 * AITransition is the pointer class of {@link AIState}, which is also the node class of the
 * AIState-AITransition LinkedList (AALL) data structure. This class points to the next AIState (node) in AALL.
 *
 * @author Mr. GodDavid
 * @see AIManager
 * @since 4/6/2026
 */
public final class AITransition {

    private final AIManager.AIStatePointer nextState;
    private final AICondition condition;

    public AITransition(AIManager.AIStatePointer nextState, AICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    public boolean shouldTransition(GameCharacter character) {
        return condition.isMet(character);
    }

    public AIManager.AIStatePointer getNextState() {
        return nextState;
    }
}
