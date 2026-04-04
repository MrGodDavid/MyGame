package entity;

import com.mrgoddavid.vector.Vector2d;
import core.GameWindow;
import entity.component.CollisionBox;
import entity.component.Size;
import core.GameLoop;

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
    protected abstract Image getSprite();

    /**
     * Update the subclass of {@code GameObject} 60 times per frame.
     *
     * @param deltaTime that is not null.
     * @see GameLoop
     */
    public abstract void update(double deltaTime);

    protected final boolean inCamera() {
        return (position.getX() >= 0
                && (position.getX() <= GameWindow.getWindowSize().getWidth()))
                && (position.getY() >= 0
                && (position.getY() <= GameWindow.getWindowSize().getHeight())
        );
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

    protected Size getSize() {
        return size;
    }
}
