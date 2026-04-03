package entity.projectile;

import com.mrgoddavid.vector.Vector2d;
import core.GameLoop;
import entity.MovingEntity;
import entity.MovingEntityManager;
import entity.component.CollisionBox;
import entity.component.Size;
import entity.enemy.Enemy;
import entity.player.Player;
import input.InputManager;

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
public class Projectile extends MovingEntity {

    public Projectile() {
        super();

        size = new Size(2, 2);
        velocity = new Vector2d(0, 0);
        collisionBox = new CollisionBox(new Rectangle(0, 0, size.getWidth(), size.getHeight()));

        speed = 50d;
        maxLife = 100;
        currentLife = maxLife;
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

        sprite = getSprite();
    }

    public void shoot(MovingEntity shooter) {
        addProjectileToMovingEntityManager(shooter.getProjectile());
        position = new Vector2d(shooter.getPosition());
        if (shooter instanceof Player player) {
            velocity = new Vector2d(InputManager.getMousePosition()).subtract(player.getPosition()).normalize().scale(speed);
        } else if (shooter instanceof Enemy enemy) {
            velocity = new Vector2d(Player.getInstance().getPosition()).subtract(enemy.getPosition()).normalize().scale(speed);
        }
    }

    private void addProjectileToMovingEntityManager(Optional<Projectile> projectile) {
        if (projectile.isPresent()) {
            Projectile copiedProjectile = Projectile.copyOf(projectile.get());
            MovingEntityManager.addMovingEntity(copiedProjectile);
        }
    }

    /**
     * Move the {@code MovingEntity} in game. This is an abstract method for defining rules of movement in subclass
     * of {@code MovingEntity}.
     *
     * @param deltaTime from {@link GameLoop}.
     */
    @Override
    public void move(double deltaTime) {
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
    protected Image getSprite() {
        BufferedImage sprite = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = sprite.createGraphics();

        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, size.getWidth(), size.getHeight());
        g2d.dispose();
        return sprite;
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
