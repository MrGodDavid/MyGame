package david.game.entity.projectile;

import com.mrgoddavid.vector.Vector2d;
import david.game.core.GameLoop;
import david.game.entity.GameCharacter;
import david.game.entity.MovingEntity;
import david.game.entity.EntityManager;
import david.game.entity.component.CollisionBox;
import david.game.entity.component.Size;
import david.game.entity.enemy.Enemy;
import david.game.entity.player.Player;
import david.game.input.InputManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * This is the superclass of all {@code projectile} in this game. The {@code projectile} class is also a subclass of
 * {@code MovingEntity} class.
 *
 * @author Mr. GodDavid
 * @since 4/2/2026
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Projectile extends MovingEntity {

    private final double damage;
    private GameCharacter shooter;

    public Projectile() {
        super();

        size = new Size(16, 16);
        velocity = new Vector2d(0, 0);
        collisionBox = new CollisionBox(new Rectangle(8, 8, size.getWidth() / 2, size.getHeight() / 2));

        speed = 500d;
        maxLife = 100;
        currentLife = maxLife;
        damage = 1.0d;

        shooter = null;

        sprite = getSprite();
    }

    private Projectile(Projectile projectile) {
        super();
        position = projectile.getPosition();
        size = projectile.getSize();
        velocity = projectile.getVelocity();
        collisionBox = projectile.getCollisionBox();

        speed = projectile.getSpeed();
        maxLife = projectile.getMaxLife();
        currentLife = projectile.getCurrentLife();
        damage = projectile.getDamage();

        shooter = projectile.getShooter();
        sprite = getSprite();
    }

    public void firedBy(GameCharacter shooter) {
        this.shooter = shooter;
        if (shooter instanceof Player player) {
            velocity = new Vector2d(InputManager.getMousePosition())
                    .subtract(player.getPosition())
                    .normalize()
                    .scale(speed);
            player.decreaseEnergy();
        } else if (shooter instanceof Enemy enemy) {
            velocity = new Vector2d(Player.getInstance().getPosition())
                    .subtract(enemy.getPosition())
                    .normalize()
                    .scale(speed);
        }
        this.position = new Vector2d(shooter.getPosition()
                .add(new Vector2d(shooter.getSize().getX() / 2d, shooter.getSize().getY() / 2d)))
                .add(velocity.scale(0.15));
        addProjectileToMovingEntityManager(shooter.getProjectile());
    }

    private void addProjectileToMovingEntityManager(Optional<Projectile> projectile) {
        if (projectile.isPresent()) {
            Projectile copiedProjectile = Projectile.copyOf(projectile.get());
            EntityManager.addProjectile(copiedProjectile);
        }
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
     * Update the subclass of {@code GameObject} 60 times per frame.
     *
     * @param deltaTime that is not null.
     * @see GameLoop
     */
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        currentLife--;
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

    public double getDamage() {
        return this.damage;
    }

    public GameCharacter getShooter() {
        return shooter;
    }

    public static Projectile copyOf(Projectile projectile) {
        return new Projectile(projectile);
    }

    /**
     * Define a String representation of this class. {@code Projectile} class contains information of "[PROJECTILE]" tag and
     * additional information from {@code MovingEntity} class.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "[PROJECTILE]: " + super.toString();
    }
}
