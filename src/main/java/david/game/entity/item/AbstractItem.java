package david.game.entity.item;

import david.game.data.ItemData;
import david.game.entity.GameObject;
import david.game.entity.component.CollisionBox;

/**
 * @author Mr. GodDavid
 * @since 4/12/2026
 */
public abstract class AbstractItem extends GameObject {

    private boolean draw;
    private ItemData data;

    public AbstractItem() {
        super();
        draw = true;
    }

    @Override
    public String toString() {
        return "[ABSTRACT ITEM]";
    }

    /**
     * Configure item data for this game item.
     *
     * @return a ItemData struct.
     */
    protected abstract ItemData configItemData();

    public boolean isCollidingWith(CollisionBox collisionBox) {
        return this.collisionBox.collidesWith(collisionBox);
    }

    public void notDrawing() {
        draw = false;
    }

    public boolean isDraw() {
        return draw;
    }

    protected void registerItemData() {
        this.data = configItemData();
    }

    protected ItemData getItemData() {
        return data;
    }
}
