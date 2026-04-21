package david.game.entity.ai;

import david.game.entity.ai.state.AIState;
import david.game.entity.GameCharacter;

/**
 * This class define condition whether the traversal pointer can continue traverse to the next
 * {@link AIState} in AALL. See {@link AIManager}.
 *
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
@FunctionalInterface
public interface AICondition {

    /**
     * Defines the condition whether the AALL traversal pointer can continue traversing the AALL.
     *
     * @param character that is not null.
     * @return true if all conditions are met.
     */
    boolean isMet(GameCharacter character);
}
