package entity.ai;

import entity.GameCharacter;

/**
 * AITransition is the pointer class of {@link entity.ai.state.AIState}, which is also the node class of the
 * AIState-AITransition LinkedList (AALL) data structure. This class points to the next AIState (node) in AALL.
 *
 * @author Mr. GodDavid
 * @see AIManager
 * @since 4/6/2026
 */
public final class AITransition {

    private final String nextState;
    private final AICondition condition;

    public AITransition(String nextState, AICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    public boolean shouldTransition(GameCharacter character) {
        return condition.isMet(character);
    }

    public String getNextState() {
        return nextState;
    }
}
