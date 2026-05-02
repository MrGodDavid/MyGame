package david.game.entity.enemy;

import com.mrgoddavid.vector.Vector2i;
import david.game.core.Game;
import david.game.core.GameLoop;
import david.game.data.CharacterData;
import david.game.entity.GameCharacter;
import david.game.entity.ai.AIManager;
import david.game.entity.component.HealthBar;
import david.game.utils.TextUtils;
import david.game.utils.Timer;

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
        super.registerCharacterData();

        position = super.getCharacterData().getPosition();
        velocity = super.getCharacterData().getVelocity();
        size = super.getCharacterData().getSize();
        collisionBox = super.getCharacterData().getCollisionBox();

        speed = super.getCharacterData().getSpeed();
        maxLife = super.getCharacterData().getMax_life();
        currentLife = maxLife;
        projectile = Optional.ofNullable(super.getCharacterData().getProjectile());
        projectileShootingCoolDownTimer = Optional.of(new Timer(120));
        healthBar = new HealthBar(maxLife);
        healthBar.setDrawHealthBar(true);

        sprite = getSprite();
    }

    /**
     * Configure a character data struct of this game character.
     *
     * @return a character data struct of this game character.
     */
    @Override
    protected CharacterData configCharacterData() {
        return Game.getConfigManager().getCharacter("enemy");
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
