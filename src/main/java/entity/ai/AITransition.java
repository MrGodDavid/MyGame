package entity.ai;

import entity.GameCharacter;

/**
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public class AITransition {

    private String nextState;
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
