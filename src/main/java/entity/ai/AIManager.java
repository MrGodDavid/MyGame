package entity.ai;

import entity.GameCharacter;
import entity.ai.state.AIState;
import entity.ai.state.Patrol;
import entity.ai.state.Wander;

/**
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public class AIManager {

    AIState currentState;

    public AIManager(String initialState) {
        transitionTo(initialState);
    }

    public void update(GameCharacter character) {
        currentState.update(character);

        if (currentState.shouldTransition(character)) {
            transitionTo(currentState.getNextState());
        }
    }

    private void transitionTo(String state) {
        currentState = switch(state) {
            case "wander" -> new Wander();
            case "patrol" -> new Patrol();
            default ->  new Wander();
        };
    }

    @Override
    public String toString() {
        if (currentState == null) return "Current state is null. Please check [transitionTo()] method of AIManager class.";
        return currentState.toString();
    }
}
