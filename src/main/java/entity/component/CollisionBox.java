package entity.component;

import entity.MovingEntity;

import java.awt.*;

/**
 * This is the wrapper class of {@link java.awt.Rectangle} class.
 * <p>
 * Essentially, a {@code CollisionBox} is the region where other {@code MovingEntity} cannot overlap with current
 * {@code MovingEntity} in game. Once the two collisionboxes overlap each other, both of them have to stop. This is
 * the collision event.
 */
public final class CollisionBox {

    private Rectangle boundingBox;

    public CollisionBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean collidesWith(CollisionBox other) {
        return this.boundingBox.intersects(other.getBoundingBox());
    }

    public void update(MovingEntity owner) {
        this.boundingBox.x = (int) Math.round(owner.getPosition().getX());
        this.boundingBox.y = (int) Math.round(owner.getPosition().getY());
    }

    private Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public int getX() {
        return boundingBox.x;
    }

    public int getY() {
        return boundingBox.y;
    }

    public int getWidth() {
        return boundingBox.width;
    }

    public int getHeight() {
        return boundingBox.height;
    }
}
