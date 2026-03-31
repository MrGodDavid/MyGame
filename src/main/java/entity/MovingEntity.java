package entity;

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
}
