package entity.item;

import entity.GameObject;
import entity.component.CollisionBox;

/**
 * @author Mr. GodDavid
 * @since 4/12/2026
 */
public abstract class AbstractItem extends GameObject {

    private boolean draw;

    public AbstractItem() {
        super();
        draw = true;
    }

    @Override
    public String toString() {
        return "[ABSTRACT ITEM]";
    }

    public boolean isCollidingWith(CollisionBox collisionBox) {
        return this.collisionBox.collidesWith(collisionBox);
    }

    public void notDrawing() {
        draw = false;
    }

    public boolean isDraw() {
        return draw;
    }
}
