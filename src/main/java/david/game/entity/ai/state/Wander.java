package david.game.entity.ai.state;

import com.mrgoddavid.vector.Vector2d;
import david.game.entity.GameCharacter;
import david.game.entity.ai.AIManager;
import david.game.entity.ai.AITransition;
import david.game.entity.player.Player;
import david.game.utils.Math;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr. GodDavid
 * @since 4/6/2026
 */
public final class Wander extends AIState {

    private final List<Vector2d> positions;

    public Wander() {
        super();
        positions = new ArrayList<>();
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(AIManager.AIStatePointer.PATROL, this::conditionOfTransitioning);
    }

    private boolean conditionOfTransitioning(GameCharacter character) {
        return character.getPosition().distance(Player.getInstance().getPosition()) < 100;
    }

    @Override
    public void update(GameCharacter character) {
        if (positions.isEmpty()) {
            positions.add(Math.getRandomPositionOnScreen());
        }

        moveTo(character, positions.getFirst());
        if (arrived(character, positions.getFirst())) {
            positions.clear();
        }
    }

    private void moveTo(GameCharacter character, Vector2d position) {
        character.setVelocity(new Vector2d(position
                .subtract(character.getPosition()))
                .normalize()
                .scale(character.getSpeed())
        );
    }

    private boolean arrived(GameCharacter character, Vector2d position) {
        return character.getPosition().distance(position) < 1;
    }

    @Override
    public String toString() {
        return "Current state is Wander.";
    }

}
