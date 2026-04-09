package entity.ai;

import entity.GameCharacter;

/**
 * This class define condition whether the traversal pointer can continue traverse to the next
 * {@link entity.ai.state.AIState} in AALL. See {@link AIManager}.
 *
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public interface AICondition {

    /**
     * Defines the condition whether the AALL traversal pointer can continue traversing the AALL.
     *
     * @param character that is not null.
     * @return true if all conditions are met.
     */
    boolean isMet(GameCharacter character);
}
