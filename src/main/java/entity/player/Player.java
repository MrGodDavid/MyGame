package entity.player;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import core.GameLoop;
import entity.MovingEntity;
import entity.component.CollisionBox;
import entity.component.Size;
import game.Game;
import input.InputManager;
import utils.TextUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Player class. Player class is a subclass of the {@code MovingEntity}. Player class implements the
 * {@link entity.GameObject#getSprite()}, {@link entity.GameObject#update(double)}, and {@link MovingEntity#move(double)}
 * methods. The player can move, shoot projectiles, and endure damages from enemies.
 * <p>
 * If player dies, the game is over. When player shoots projectiles and kills enemy, the player gains scores and earns
 * money and experience points. The player can use money to buy skills and equipments to increase its life points, damage
 * strength, and additional awards.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Player extends MovingEntity {

    public Player() {
        super();
        position = new Vector2d(100, 100);
        velocity = new Vector2d(0, 0);
        size = new Size(48, 48);
        collisionBox = new CollisionBox(new Rectangle(0, 0, 48, 48));

        speed = 200d;
        maxLife = 10;
        currentLife = maxLife;
        alive = true;

        sprite = getSprite();
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
        String text = "ME";
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

    @Override
    public void move(double deltaTime) {
        Vector2d mousePosition = new Vector2d(InputManager.getMousePosition());
        Vector2d direction = mousePosition.subtract(position).normalize();
        this.velocity = direction.scale(speed);
    }

    @Override
    public String toString() {
        return "[PLAYER]: " + super.toString();
    }
}
