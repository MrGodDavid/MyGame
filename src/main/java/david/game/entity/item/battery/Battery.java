package david.game.entity.item.battery;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import david.game.core.Game;
import david.game.core.GameLoop;
import david.game.data.ItemData;
import david.game.entity.item.AbstractItem;
import david.game.utils.TextUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mr. GodDavid
 * @since 5/1/2026
 */
public final class Battery extends AbstractItem {

    public Battery(final Vector2d position) {
        super();
        super.registerItemData(); // must call this method after super().

        this.position = position;
        this.size = super.getItemData().getSize();
        this.collisionBox = super.getItemData().getCollisionBox();
    }

    /**
     * Return the sprite of the subclass of {@code GameObject}.
     *
     * @return the sprite of the subclass of {@code GameObject}.
     */
    @Override
    public Image getSprite() {
        BufferedImage sprite = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = sprite.createGraphics();

        String text = super.getItemData().getName().substring(0, 1).toUpperCase()
                + super.getItemData().getName().substring(1);
        Vector2i centeredPosition = TextUtils.getCenteredFontPosition(g2d, text, size);

        g2d.setFont(Game.getGameFont().deriveFont(Font.PLAIN, 24F));
        g2d.setColor(new Color(255, 255, 255, 255));
        g2d.drawString(
                text,
                centeredPosition.getX(),
                centeredPosition.getY()
        );
        g2d.dispose();

        return sprite;
    }

    /**
     * Update the subclass of {@code GameObject} 60 times per frame.
     *
     * @param deltaTime that is not null.
     * @see GameLoop
     */
    @Override
    public void update(double deltaTime) {
        this.collisionBox.update(this);
    }

    public int getEnergy() {
        return super.getItemData().getEnergy();
    }

    /**
     * Configure item data for this game item.
     *
     * @return a ItemData struct.
     */
    @Override
    protected ItemData configItemData() {
        return Game.getConfigManager().getItem("battery");
    }
}
