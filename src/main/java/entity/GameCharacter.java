package entity;

import core.GameLoop;
import entity.projectile.Projectile;
import utils.Timer;

import java.util.Optional;

/**
 * This is the superclass for {@code Player} and the {@code Enemy}. This class is also the subclass of
 * {@code MovingEntity}.
 */
public abstract class GameCharacter extends MovingEntity {

    protected Optional<Projectile> projectile;
    protected Optional<Timer> projectileShootingCoolDownTimer;

    public GameCharacter() {
        super();
        projectile = Optional.empty();
        projectileShootingCoolDownTimer = Optional.empty();
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
