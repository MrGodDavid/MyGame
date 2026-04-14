package entity.player;

import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import entity.GameCharacter;
import entity.component.CollisionBox;
import entity.component.Size;
import entity.item.AbstractItem;
import entity.projectile.Projectile;
import core.Game;
import entity.component.HealthBar;
import input.InputManager;
import utils.TextUtils;
import utils.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Player class. Player class is a subclass of the {@code MovingEntity}. Player class implements the
 * {@link entity.GameObject#getSprite()} and {@link entity.GameObject#update(double)}
 * methods. The player can move, shoot projectiles, and endure damages from enemies.
 * <p>
 * If player dies, the game is over. When player shoots projectiles and kills enemy, the player gains scores and earns
 * money and experience points. The player can use money to buy skills and equipments to increase its life points, damage
 * strength, and additional awards.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Player extends GameCharacter {

    private static Player instance;
    private final Set<AbstractItem> inventory;

    private Player() {
        super();
        this.inventory = new HashSet<>();
        position = new Vector2d(100, 100);
        velocity = new Vector2d(0, 0);
        size = new Size(48, 48);
        collisionBox = new CollisionBox(new Rectangle(0, 0, 48, 48));

        // player attributes.
        speed = 200d;
        maxLife = 10;
        currentLife = maxLife;
        projectile = Optional.of(new Projectile());
        projectileShootingCoolDownTimer = Optional.of(new Timer(60));
        healthBar = new HealthBar(maxLife);

        sprite = getSprite();
    }

    /**
     * Returns the single instance of {@code Player}. This method initializes the {@code Player} if the
     * instance is not being initialized. Otherwise, returns the instance of {@code Player}.
     *
     * @return the only instance of {@code Player}
     */
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
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

    /**
     * Move the {@code MovingEntity} in game. This is an abstract method for defining rules of movement in subclass
     * of {@code MovingEntity}.
     *
     */
    public void move() {
        Vector2d mousePosition = new Vector2d(InputManager.getMousePosition());
        Vector2d direction = mousePosition.subtract(position).normalize();
        this.velocity = direction.scale(speed);
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

    public void pickUp(AbstractItem item) {
        inventory.add(item);
    }

    /**
     * Define a String representation of this class. {@code Player} class contains information of "[PLAYER]" tag and
     * additional information from {@code MovingEntity} class.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "[PLAYER]: " + super.toString();
    }

    public void printInventory() {
        System.out.println(inventory.toString());
    }
}
