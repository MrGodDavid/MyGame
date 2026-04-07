package entity.ai;

import entity.GameCharacter;

/**
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public interface AICondition {

    boolean isMet(GameCharacter character);
}
