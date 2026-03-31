package entity.player;

import com.mrgoddavid.vector.Vector2d;
import core.GameLoop;
import entity.GameObject;
import entity.component.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Player class.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Player extends GameObject {

    public Player() {
        position = new Vector2d(100, 100);
        velocity = new Vector2d(10, 0);
        size = new Size(100, 100);
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

        g2d.setColor(Color.RED);
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
        position = position.add(velocity.scale(deltaTime));
    }
}
