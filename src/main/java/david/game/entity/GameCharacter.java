package david.game.entity;

import com.mrgoddavid.vector.Vector2d;
import david.game.core.GameLoop;
import david.game.entity.component.ShootPath;
import david.game.entity.player.Player;
import david.game.entity.projectile.Projectile;
import david.game.entity.component.HealthBar;
import david.game.input.InputManager;
import david.game.input.MouseInputListener;
import david.game.utils.Math;
import david.game.utils.Timer;

import java.awt.*;
import java.util.Optional;

/**
 * This is the superclass for {@code Player} and the {@code Enemy}. This class is also the subclass of
 * {@code MovingEntity}.
 *
 * @author Mr. GodDavid
 * @since 4/2/2026
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class GameCharacter extends MovingEntity {

    public static final int SPRITE_SIZE = 64;

    protected Optional<Projectile> projectile;
    protected Optional<Timer> projectileShootingCoolDownTimer;
    protected HealthBar healthBar;
    protected ShootPath shootPath;

    public GameCharacter() {
        super();
        projectile = Optional.empty();
        projectileShootingCoolDownTimer = Optional.empty();
        healthBar = null;
        shootPath = null;
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
        projectileShootingCoolDownTimer.ifPresent(Timer::update);
        // ===== UPDATE HEALTH BAR =====
        if (healthBar != null) {
            healthBar.update(currentLife);
        }
    }

    /**
     * Render game character's graphics components, such as health bar, particles, etc.
     *
     * @param g2d acts like a rendering pipeline.
     * @apiNote This method DOES NOT draw game character itself.
     */
    public void render(Graphics2D g2d) {
        if (healthBar != null) {
            healthBar.render(g2d, Math.toVector2i(position), size);
        }
        if (shootPath != null
                && this instanceof Player
                && InputManager.isButtonPressed(MouseInputListener.MouseButton.RIGHT_BUTTON)) {
            shootPath.render(g2d);
        }
    }

    public void shoot() {
        projectileShootingCoolDownTimer.ifPresent(timer -> {
            if (timer.isFinished()) {
                projectile.ifPresent(projectile -> projectile.firedBy(this));
                timer.reset();
            }
        });
    }

    public Optional<Projectile> getProjectile() {
        return projectile;
    }
}
