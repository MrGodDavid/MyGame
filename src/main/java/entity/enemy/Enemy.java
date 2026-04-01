package entity.enemy;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import core.GameLoop;
import entity.MovingEntity;
import entity.MovingEntityManager;
import entity.component.CollisionBox;
import entity.component.Size;
import game.Game;
import utils.TextUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Superclass of all enemies in this game.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
public class Enemy extends MovingEntity {

    public Enemy() {
        position = new Vector2d(-1, -1);
        size = new Size(64, 64);
        collisionBox = new CollisionBox(new Rectangle(0, 0, 48, 48));
        speed = 100;

        sprite = getSprite();
    }

    /**
     * Update the subclass of {@code GameObject} 60 times per frame.
     *
     * @param deltaTime that is not null.
     * @see GameLoop
     */
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        move(deltaTime);
    }

    @Override
    public void move(double deltaTime) {
        if (!isMetPlayer()) {
            Vector2d playerPosition = new  Vector2d(MovingEntityManager.getPlayer().getPosition());
            Vector2d direction = playerPosition.subtract(position).normalize();
            this.velocity = direction.scale(speed);
        }
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
        String text = "Enemy";
        Vector2i centeredPosition = TextUtils.getCenteredFontPosition(g2d, text, size);

        g2d.setFont(Game.getGameFont().deriveFont(Font.PLAIN, 24F));
        g2d.setColor(new Color(255, 255, 255, 255));
        g2d.drawString(text, centeredPosition.getX(), centeredPosition.getY());
        g2d.dispose();

        return sprite;
    }

    public boolean isMetPlayer() {
        return this.isCollidingWith(MovingEntityManager.getPlayer().getCollisionBox());
    }
}
