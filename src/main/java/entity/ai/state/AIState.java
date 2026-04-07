package entity.ai.state;

import entity.GameCharacter;
import entity.ai.AITransition;

/**
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public abstract class AIState {

    private AITransition transition;

    public AIState() {
        transition = initializeTransition();
    }

    protected abstract AITransition initializeTransition();

    public abstract void update(GameCharacter character);

    public boolean shouldTransition(GameCharacter character) {
        return transition.shouldTransition(character);
    }

    public String getNextState() {
        return transition.getNextState();
    }
}
