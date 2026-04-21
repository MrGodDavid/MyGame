package david.game.entity.component;

import com.mrgoddavid.vector.Vector2i;
import david.game.entity.EntityManager;
import david.game.entity.GameCharacter;

import java.awt.*;

/**
 * The health bar component for game character. This class creates a health bar beneath the game character.
 * The health bar is a rectangle in which its color ranges from red (rgb: 255, 0, 0) to green (rgb: 0, 255, 0) based
 * on the game character's percentage of its current health to its maximum health. The length of the rectangle
 * health bar is based on the percentage of its current health of its maximum health.
 * <p>
 * The health bar class contains an update method that updates the color of the rectangle and the width of the
 * rectangle.
 * <p>
 * The render method of this class renders the rectangle through {@link Graphics2D} rendering pipeline that passed
 * by the rendering pipeline from {@link EntityManager}.
 * <p>
 * By default, the drawing boolean flag is false, which means the health bar would not be drawn in this game, even
 * though the health bar in game character is not null. In order to draw the health bar of game character, user
 * must use {@link HealthBar#setDrawHealthBar(boolean)} method and input {@code true} to let the game draw the health
 * bar of game character.
 *
 * @author Mr. GodDavid
 * @since 4/10/2026
 */
public final class HealthBar {

    private final Rectangle healthBar;

    private final double maxHealth;
    private double percentage;
    private int red;
    private int green;

    private boolean drawHealthBar;

    public HealthBar(double maxHealth) {
        healthBar = new Rectangle(0, 0, GameCharacter.SPRITE_SIZE, 8);
        this.maxHealth = maxHealth;
        percentage = 0d;
        red = 0;
        green = 255;
        drawHealthBar = false;
    }

    public void update(double currentLife) {
        percentage = ((((currentLife - 0.0001) > 0) ? currentLife : 0) / maxHealth);
        red = (int) (255 * (1 - percentage));
        green = (int) (255 * percentage);
    }

    public void render(Graphics2D g2d, Vector2i position, Size size) {
        if (!drawHealthBar) return;
        Vector2i healthBarPosition = position.add(new Vector2i(0, size.getHeight() + 10));

        // BACKGROUND
        g2d.setColor(new Color(37, 37, 37));
        g2d.fillRect(healthBarPosition.getX() - 1, healthBarPosition.getY() - 1, healthBar.width + 1, healthBar.height + 1);

        // HEALTH BAR
        int xOffset = (healthBar.width - (int) (healthBar.width * (percentage))) / 2;
        g2d.setColor(new Color(red, green, 0));
        g2d.fillRect(healthBarPosition.getX() + xOffset, healthBarPosition.getY(), (int) (healthBar.width * (percentage)), healthBar.height);
    }

    @Override
    public String toString() {
        return "Health bar: percentage=" + percentage + ", red=" + red + ", green=" + green;
    }

    /**
     * Enable the game to draw health bar of game character. Simply set the {@code drawHealthBar} parameter to true.
     * <p>
     * Code of void setDrawHealthBar(boolean drawHealthBar):
     * <pre><code>
     * public void setDrawHealthBar(boolean drawHealthBar) {
     *     this.drawHealthBar = drawHealthBar;
     * }
     * </code></pre>
     *
     * @param drawHealthBar boolean flag that determines whether the game should draw health bar of game character.
     */
    public void setDrawHealthBar(boolean drawHealthBar) {
        this.drawHealthBar = drawHealthBar;
    }
}
