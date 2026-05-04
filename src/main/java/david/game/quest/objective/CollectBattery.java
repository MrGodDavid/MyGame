package david.game.quest.objective;

import david.game.entity.item.battery.Battery;
import david.game.entity.player.Player;
import david.game.quest.ObjectiveTransition;
import david.game.quest.QuestManager;

/**
 * Collecting battery objective.
 *
 * @author Mr. GodDavid
 * @since 5/3/2026
 */
public final class CollectBattery extends Objective {

    private int numOfBatteryCollected;

    public CollectBattery() {
        super();
        title = "Objective";
        description = "Collect three \nbatteries.";
        numOfBatteryCollected = 0;
    }

    /**
     * Initializes the ObjectiveTransition pointer class of a specific subclass of {@code Objective}.
     *
     * @return the constructed ObjectiveTransition, which is the pointer class, in OOLL.
     */
    @Override
    protected ObjectiveTransition initializeTransition() {
        return new ObjectiveTransition(QuestManager.ObjectivePointer.NULL, this::requirement);
    }

    private boolean requirement() {
        return numOfBatteryCollected == 3;
    }

    /**
     * Updates the Objective in game loop. The update could be updating the number of kills of enemy in
     * EnemyKillingObjective.
     */
    @Override
    public void update() {
        if (Player.getPickUpItem() instanceof Battery) {
            numOfBatteryCollected++;
        }
    }

    /**
     * Check if the current objective is finished. Finishing conditions are specified in child classes.
     *
     * @return true if all conditions of this objective are met.
     * @apiNote This method DOES NOT define the transitioning condition.
     */
    @Override
    public boolean isFinished() {
        return numOfBatteryCollected == 3;
    }

    @Override
    public String toString() {
        return "Current objective is collecting three batteries.";
    }
}
