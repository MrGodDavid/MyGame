package david.game.data;

import david.game.entity.projectile.Projectile;

/**
 * The name of the data field must be the SAME as the name of the key in
 * JSON Config file.
 *
 * @author Mr. GodDavid
 * @since 5/1/2026
 */
public final class CharacterData {

    private String name;
    private String projectile;
    private int max_life;
    private int max_energy;
    private int speed;

    CharacterData() {
        name = "NULL";
        projectile = "NULL";
        max_life = -999;
        max_energy = -999;
    }

    public int getMax_energy() {
        return max_energy;
    }

    public int getMax_life() {
        return max_life;
    }

    public String getName() {
        return name;
    }

    public Projectile getProjectile() {
        return switch (projectile) {
            case "projectile" -> new Projectile();
            case "NULL" -> null;
            default -> throw new IllegalStateException("Unexpected value: " + projectile);
        };
    }

    public int getSpeed() {
        return speed;
    }
}
