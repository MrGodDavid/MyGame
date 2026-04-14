package quest;

/**
 * Define the requirement to finish one objective/quest in game. This interface contains one method that check
 * if the player has met all the requirements of one objective/quest in game.
 *
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
@FunctionalInterface
public interface Requirement {

    /**
     * Checks if player has met all requirements of objective/quest in game.
     *
     * @return true if player has met all requirements of objective/quest in game.
     */
    boolean isMet();
}
