package david.game.data;

/**
 * The name of the data field must be the SAME as the name of the key in
 * JSON Config file.
 *
 * @author Mr. GodDavid
 * @since 5/1/2026
 */
public final class ItemData {

    private String name;
    private int heal_effort;
    private int energy;

    ItemData() {
        name = "NULL";
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
}
