package david.game.data;

import com.mrgoddavid.vector.Vector2d;
import david.game.entity.component.CollisionBox;
import david.game.entity.component.Size;
import david.game.entity.projectile.Projectile;

import java.awt.*;

/**
 * The name of the data field must be the SAME as the name of the key in
 * JSON Config file.
 *
 * @author Mr. GodDavid
 * @since 5/1/2026
 */
public final class CharacterData {

    private final String name;
    private final String position;
    private final String velocity;
    private final String size;
    private final String collision_box;
    private final String projectile;
    private final int max_life;
    private final int max_energy;
    private final int speed;

    CharacterData() {
        name = "NULL";
        projectile = "NULL";
        position = "NULL";
        velocity = "NULL";
        size = "NULL";
        collision_box = "NULL";
        max_life = -999;
        max_energy = -999;
        speed = 0;
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

    public Vector2d getPosition() {
        if (position.equals("NULL")) {
            System.out.println("[WARNING]: Field \"position\" is NULL!");
            return new Vector2d(0, 0);
        }
        String[] tokens = position.split(ObjectMapper.DATA_DELIMITER);
        return new Vector2d(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
    }

    public Vector2d getVelocity() {
        if (velocity.equals("NULL")) {
            System.out.println("[WARNING]: Field \"velocity\" is NULL!");
            return new Vector2d(0, 0);
        }
        String[] tokens = velocity.split(ObjectMapper.DATA_DELIMITER);
        return new Vector2d(Double.parseDouble(tokens[0].trim()), Double.parseDouble(tokens[1].trim()));
    }

    public Size getSize() {
        if (size.equals("NULL")) {
            System.out.println("[WARNING]: Field \"size\" is NULL!");
            return new Size(1, 1);
        }
        String[] tokens = size.split(ObjectMapper.DATA_DELIMITER);
        return new Size(Integer.parseInt(tokens[0].trim()), Integer.parseInt(tokens[1].trim()));
    }

    public CollisionBox getCollisionBox() {
        if (collision_box.equals("NULL")) {
            System.out.println("[WARNING]: Field \"collision_box\" is NULL!");
            return new CollisionBox(new Rectangle(0, 0, 1, 1));
        }
        String[] tokens = collision_box.split(ObjectMapper.DATA_DELIMITER);
        Rectangle boundingBox = new Rectangle(
                Integer.parseInt(tokens[0].trim()),
                Integer.parseInt(tokens[1].trim()),
                Integer.parseInt(tokens[2].trim()),
                Integer.parseInt(tokens[3].trim())
        );
        return new CollisionBox(boundingBox);
    }
}
