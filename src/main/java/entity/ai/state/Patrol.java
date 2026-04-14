package entity.ai.state;

import com.mrgoddavid.vector.Vector2d;
import entity.GameCharacter;
import entity.EntityManager;
import entity.ai.AIManager;
import entity.ai.AITransition;
import entity.player.Player;

/**
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public final class Patrol extends AIState {

    public Patrol() {
        super();
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(AIManager.AIStatePointer.WANDER, this::conditionOfTransition);
    }

    private boolean conditionOfTransition(GameCharacter character) {
        return character.getPosition().distance(Player.getInstance().getPosition()) > 400;
    }

    @Override
    public void update(GameCharacter character) {
        if (!isMetPlayer(character)) {
            Vector2d playerPosition = EntityManager.getPlayer().getPosition();
            Vector2d direction = playerPosition.subtract(character.getPosition()).normalize();
            character.setVelocity(direction.scale(character.getSpeed()));
        }
    }

    private boolean isMetPlayer(GameCharacter character) {
        return character.isCollidingWith(EntityManager.getPlayer().getCollisionBox());
    }

    @Override
    public String toString() {
        return "Current state is Patrol.";
    }
}
