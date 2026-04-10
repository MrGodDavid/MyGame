package entity.component;

import com.mrgoddavid.vector.Vector2i;
import entity.GameCharacter;

import java.awt.*;

/**
 * The health bar component for game character.
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
        percentage = (currentLife / maxHealth);
        red = (int) (255 * (1 - percentage));
        green = (int) (255 * percentage);
    }

    public void render(Graphics2D g2d, Vector2i position, Size size) {
        if (!drawHealthBar) return;
        Vector2i healthBarPosition = position.add(new Vector2i(0, size.getHeight() + 10));
        g2d.setColor(new Color(red, green, 0));
        g2d.fillRect(healthBarPosition.getX(), healthBarPosition.getY(), healthBar.width, healthBar.height);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(healthBarPosition.getX(), healthBarPosition.getY(), (int) (healthBar.width * (1d - percentage)), healthBar.height);
    }

    @Override
    public String toString() {
        return "Health bar: percentage=" + percentage + ", red=" + red + ", green=" + green;
    }

    public void setDrawHealthBar(boolean drawHealthBar) {
        this.drawHealthBar = drawHealthBar;
    }
}
