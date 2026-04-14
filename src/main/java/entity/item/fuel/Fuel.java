package entity.item.fuel;

import com.mrgoddavid.vector.Vector2d;
import core.GameLoop;
import entity.GameCharacter;
import entity.component.CollisionBox;
import entity.component.Size;
import entity.item.AbstractItem;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mr. GodDavid
 * @since 4/13/2026
 */
public final class Fuel extends AbstractItem {

    public Fuel() {
        super();
        this.position = new Vector2d(0, 0);
        this.size = new Size();
        this.collisionBox = new CollisionBox(new Rectangle());
    }

    public Fuel(final Vector2d position) {
        super();
        this.position = position;
        this.size = new Size(GameCharacter.SPRITE_SIZE,  GameCharacter.SPRITE_SIZE);
        this.collisionBox = new CollisionBox(new Rectangle(
                0, 0, GameCharacter.SPRITE_SIZE, GameCharacter.SPRITE_SIZE
        ));
    }

    /**
     * Return the sprite of the subclass of {@code GameObject}.
     *
     * @return the sprite of the subclass of {@code GameObject}.
     */
    @Override
    protected Image getSprite() {
        BufferedImage sprite = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = sprite.createGraphics();

        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, size.getWidth(), size.getHeight());
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
}
