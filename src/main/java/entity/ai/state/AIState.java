package entity.ai.state;

import entity.GameCharacter;
import entity.ai.AIManager;
import entity.ai.AITransition;

/**
 * This is the superclass for all states of {@code GameCharacter} of this game. State defines the overall of game
 * character's behaviors.
 * <p>
 * For instance, if the state of an enemy is wandering, the enemy walks to different random  positions across the map.
 * Another example is when the state of an enemy is patrolling, the enemy chases the player unless either it reaches
 * the player's position, or the player escapes the enemy's endurance range. (Further than a certain distance from
 * player.)
 * <p>
 * AIState is the Node of the LinkedList linear data structure that consists of {@link AIState} and {@link AITransition}.
 * AIState has a pointer that points to the reference of {@code AITransition}.
 * <p>
 * The constructor of AIState initializes the AITransition pointer by called the {@link AIState#initializeTransition()}.
 * <p>
 * AIState contains an abstract {@link AIState#update(GameCharacter)}, a {@link AIState#shouldTransition(GameCharacter)},
 * and a getter for the AITransition pointer. {@link AIState#getNextState()}.
 *
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public abstract class AIState {

    private final AITransition transition;

    public AIState() {
        transition = initializeTransition();
    }

    /**
     * Initialize pointer that points to the next AITransition in the initial AIState-AITransition-LinkedList (AALL)
     * data structure.
     *
     * @return the initialized AITransition pointer.
     * @see entity.ai.AIManager more information about AIState-AITransition-LinkedList (AALL)
     */
    protected abstract AITransition initializeTransition();

    /**
     * Update the subclass state of AIState.
     *
     * @param character that is not null.
     */
    public abstract void update(GameCharacter character);

    /**
     * Wrapper class of {@link AITransition#shouldTransition(GameCharacter)}.
     *
     * @param character that is not null.
     * @return true if current state should transit to next state.
     */
    public boolean shouldTransition(GameCharacter character) {
        return transition.shouldTransition(character);
    }

    /**
     * This method returns the pointer (in this case we use a {@code String}) that points to the next state.
     *
     * @return a {@code String} pointer that points to the next state.
     */
    public AIManager.AIStatePointer getNextState() {
        return transition.getNextState();
    }
}
