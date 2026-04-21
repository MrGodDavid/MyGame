package david.game.entity.item.fuel;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import david.game.core.Game;
import david.game.core.GameLoop;
import david.game.entity.GameCharacter;
import david.game.entity.component.CollisionBox;
import david.game.entity.component.Size;
import david.game.entity.item.AbstractItem;
import david.game.utils.TextUtils;

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
    public Image getSprite() {
        BufferedImage sprite = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = sprite.createGraphics();

        String text = "Fuel";
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
}
