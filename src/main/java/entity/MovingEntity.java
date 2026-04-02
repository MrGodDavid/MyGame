package entity;

import core.GameLoop;
import entity.component.CollisionBox;

import java.awt.*;

/**
 * Superclass for all MovingEntity of this game. This is a subclass of {@link GameObject}.
 */
public abstract class MovingEntity extends GameObject {

    protected double speed;
    protected double currentLife;
    protected double maxLife;
    protected boolean alive;

    public MovingEntity() {
        super();
    }

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

    public boolean isMetPlayer() {
        return this.isCollidingWith(MovingEntityManager.getPlayer().getCollisionBox());
    }

    protected boolean isCollidingWith(CollisionBox otherCollisionBox) {
        return this.collisionBox.collidesWith(otherCollisionBox);
    }

    public CollisionBox getCollisionBox() {
        return this.collisionBox;
    }

    public void damage(int damage) {
        this.currentLife -= damage;
    }

    @Override
    public String toString() {
        return "position=" + position
                + ", velocity=" + velocity
                + ", collisionBox=" + collisionBox
                + ", currentLife=" + currentLife
                + ", maxLife=" + maxLife;
    }
}
