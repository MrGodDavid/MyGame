package david.game.entity;

import david.game.core.GameLoop;
import david.game.entity.component.CollisionBox;

/**
 * Superclass for all MovingEntity of this game. This is a subclass of {@link GameObject}.
 */
public abstract class MovingEntity extends GameObject {

    protected double speed;
    protected double currentLife;
    protected double maxLife;

    public MovingEntity() {
        super();
    }

    /**
     * Define the condition that the {@code MovingEntity} is alive or dead. The condition is defined in subclass's
     * implementation of this method.
     *
     * @return true if {@code MovingEntity} is still alive.
     */
    public abstract boolean isAlive();

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

    public boolean isCollidingWith(CollisionBox otherCollisionBox) {
        return this.collisionBox.collidesWith(otherCollisionBox);
    }

    public void damage(double damage) {
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

    public double getSpeed() {
        return speed;
    }

    public double getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(double currentLife) {
        this.currentLife = currentLife;
    }

    public double getMaxLife() {
        return maxLife;
    }
}
