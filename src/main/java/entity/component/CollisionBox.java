package entity.component;

import entity.GameObject;
import entity.MovingEntity;

import java.awt.*;

/**
 * This is the wrapper class of {@link java.awt.Rectangle} class.
 * <p>
 * Essentially, a {@code CollisionBox} is the region where other {@code MovingEntity} cannot overlap with current
 * {@code MovingEntity} in game. Once the two collision boxes overlap each other, both of them have to stop. This is
 * the collision event.
 */
public final class CollisionBox {

    private final Rectangle boundingBox;

    public CollisionBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * This is the wrapper method of {@link Rectangle#intersects(Rectangle)}. Checks if two moving entities' collision
     * box intersects with each other. This method returns true if two moving entities' collision box intersects each
     * other.
     *
     * @param other moving entity in the game.
     * @return true if two moving entities' collision box intersects each other.
     */
    public boolean collidesWith(CollisionBox other) {
        return this.boundingBox.intersects(other.getBoundingBox());
    }

    /**
     * This method update the position of the collision box of a {@code MovingEntity}. The location of a MovingEntity's
     * collision box is the sum of the {@code Position} of the moving entity plus the {@code Offset} of the collision
     * box of the moving entity.
     *
     * @param owner that is the moving entity in the game.
     */
    public <T extends GameObject>void update(T owner) {
        this.boundingBox.x = (int) Math.round(owner.getPosition().getX());
        this.boundingBox.y = (int) Math.round(owner.getPosition().getY());
    }

    // =============================================== [GETTERS & SETTERS] ===============================================

    private Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public String toString() {
        return "Collision Box: "
                + "[x=" + this.boundingBox.x
                + ", y=" + this.boundingBox.y
                + ", width=" + this.boundingBox.getWidth()
                + ", height=" + this.boundingBox.getHeight();
    }
}
