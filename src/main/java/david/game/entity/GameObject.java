package david.game.entity;

import com.mrgoddavid.vector.Vector2d;
import david.game.core.GameWindow;
import david.game.entity.component.CollisionBox;
import david.game.entity.component.Size;
import david.game.core.GameLoop;

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

    protected Image sprite;
    protected CollisionBox collisionBox;

    public GameObject() {
        position = new Vector2d();
        velocity = new Vector2d();
        size = new Size();
        sprite = null;
        collisionBox = null;
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
     * @see GameLoop
     */
    public abstract void update(double deltaTime);

    public final boolean inCamera() {
        return (position.getX() >= 0
                && (position.getX() <= GameWindow.getWindowSize().getWidth()))
                && (position.getY() >= 0
                && (position.getY() <= GameWindow.getWindowSize().getHeight())
        );
    }

    public final boolean hasCollisionBox() {
        return collisionBox != null;
    }

    // =============================================== [GETTERS & SETTERS] ===============================================

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public Size getSize() {
        return size;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
