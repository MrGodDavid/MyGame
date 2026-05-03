package david.game.data;

import david.game.entity.component.CollisionBox;
import david.game.entity.component.Size;

import java.awt.*;

/**
 * The name of the data field must be the SAME as the name of the key in
 * JSON Config file.
 *
 * @author Mr. GodDavid
 * @since 5/1/2026
 */
public final class ItemData {

    private final String name;
    private final String size;
    private final String collision_box;
    private final int heal_effort;
    private final int energy;

    ItemData() {
        name = "NULL";
        size = "NULL";
        collision_box = "NULL";
        heal_effort = -999;
        energy = -999;
    }

    public String getName() {
        return name;
    }

    public int getHealEffort() {
        return heal_effort;
    }

    public int getEnergy() {
        return energy;
    }

    public Size getSize() {
        double[] nums = ConfigManager.GUSParser.parseNumberPair(size);
        return new Size((int) nums[0], (int) nums[1]);
    }

    public CollisionBox getCollisionBox() {
        double[] nums = ConfigManager.GUSParser.parseNumberPair(collision_box);
        return new CollisionBox(new Rectangle(
                (int) nums[0], (int) nums[1], (int) nums[2], (int) nums[3]
        ));
    }
}
