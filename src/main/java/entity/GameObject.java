package entity;

import com.mrgoddavid.vectorMath.Vector2d;
import entity.component.Size;

import java.awt.*;

/**
 * The superclass for all game entities.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public abstract class GameObject {

    protected Vector2d position;
    protected Vector2d velocity;
    protected Size size;

    public GameObject() {
        position = new Vector2d();
        velocity = new Vector2d();
        size = new Size();
    }

    /**
     * Return the sprite of the subclass of {@code GameObject}.
     *
     * @return the sprite of the subclass of {@code GameObject}.
     */
    public abstract Image getSprite();

    /**
     * Update the subclass of {@code GameObject} 60 times per frame.
     *
     * @param deltaTime that is not null.
     * @see core.GameLoop
     */
    public abstract void update(double deltaTime);

    public Vector2d getPosition() {
        return position;
    }
}
