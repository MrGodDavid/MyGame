package entity;

import com.mrgoddavid.vector.Vector2d;
import core.GameLoop;
import entity.component.CollisionBox;

/**
 * Superclass for all MovingEntity of this game. This is a subclass of {@link GameObject}.
 */
public abstract class MovingEntity extends GameObject {

    public MovingEntity() {
        super();
    }

    protected double speed;

    public abstract void move(double deltaTime);

    public final void stop() {
        this.velocity = this.velocity.scale(0);
    }

    /**
     * Update the subclass of {@code GameObject} 60 times per frame.
     *
     * @param deltaTime that is not null.
     * @see GameLoop
     */
    @Override
    public void update(double deltaTime) {
        position = position.add(velocity.scale(deltaTime));
        collisionBox.update(this);
    }

    protected boolean isCollidingWith(CollisionBox otherCollisionBox) {
        return this.collisionBox.collidesWith(otherCollisionBox);
    }

    public CollisionBox getCollisionBox() {
        return this.collisionBox;
    }
}
