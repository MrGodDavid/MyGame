package bad.game.entity.enemy;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import bad.game.core.GameLoop;
import bad.game.entity.GameCharacter;
import bad.game.entity.ai.AIManager;
import bad.game.entity.component.CollisionBox;
import bad.game.entity.component.Size;
import bad.game.entity.projectile.Projectile;
import bad.game.core.Game;
import bad.game.entity.component.HealthBar;
import bad.game.utils.TextUtils;
import bad.game.utils.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Superclass of all enemies in this game. This class is also a subclass of {@code MovingEntity}.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
public class Enemy extends GameCharacter {

    private final AIManager aiManager;

    public Enemy() {
        aiManager = new AIManager(AIManager.AIStatePointer.WANDER);

        position = new Vector2d(-1d, -1d);
        size = new Size(64, 64);
        collisionBox = new CollisionBox(new Rectangle(0, 0, 48, 48));

        speed = 100;
        maxLife = 10;
        currentLife = maxLife;
        projectile = Optional.of(new Projectile());
        projectileShootingCoolDownTimer = Optional.of(new Timer(120));
        healthBar = new HealthBar(maxLife);
        healthBar.setDrawHealthBar(true);

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
        aiManager.update(this);
    }

    /**
     * Define the condition that the {@code MovingEntity} is alive or dead. The condition is defined in subclass's
     * implementation of this method.
     *
     * @return true if {@code MovingEntity} is still alive.
     */
    @Override
    public boolean isAlive() {
        return currentLife > 0;
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
        String text = "Enemy";
        Vector2i centeredPosition = TextUtils.getCenteredFontPosition(g2d, text, size);

        g2d.setFont(Game.getGameFont().deriveFont(Font.PLAIN, 24F));
        g2d.setColor(new Color(255, 255, 255, 255));
        g2d.drawString(text, centeredPosition.getX(), centeredPosition.getY());
        g2d.dispose();

        return sprite;
    }

    /**
     * Define a String representation of this class. {@code Enemy} class contains information of "[ENEMY]" tag and
     * additional information from {@code MovingEntity} class.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "[ENEMY]: " + super.toString();
    }
}
