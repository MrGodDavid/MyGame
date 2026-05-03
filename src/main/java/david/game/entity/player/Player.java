package david.game.entity.player;

import bad.code.format.annotation.DebugMethod;
import com.mrgoddavid.vector.Vector;
import com.mrgoddavid.vector.Vector2d;
import com.mrgoddavid.vector.Vector2i;
import david.game.core.Game;
import david.game.core.GameLoop;
import david.game.data.gus.CharacterData;
import david.game.entity.GameCharacter;
import david.game.entity.GameObject;
import david.game.entity.component.HealthBar;
import david.game.entity.component.ShootPath;
import david.game.entity.item.AbstractItem;
import david.game.entity.item.battery.Battery;
import david.game.entity.item.fuel.Fuel;
import david.game.input.InputManager;
import david.game.input.MouseInputListener;
import david.game.utils.TextUtils;
import david.game.utils.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Player class. Player class is a subclass of the {@code MovingEntity}. Player class implements the
 * {@link GameObject#getSprite()} and {@link GameObject#update(double)}
 * methods. The player can move, shoot projectiles, and endure damages from enemies.
 * <p>
 * If player dies, the game is over. When player shoots projectiles and kills enemy, the player gains scores and earns
 * money and experience points. The player can use money to buy skills and equipments to increase its life points, damage
 * strength, and additional awards.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
@SuppressWarnings("unused")
public final class Player extends GameCharacter {

    /**
     * This private static class stores the player's statistics in this game.
     */
    private static class PlayerStat {

        // player attribute constants.
        private static final int ORBIT_RADIUS = Game.UNIT_LENGTH;

        // player's in-game attributes.
        private int energy;

        // game states.
        private boolean isOrbiting;

        private PlayerStat() {
            this.energy = 10;
            this.isOrbiting = false;
        }

        /**
         * Increment the energy of player.
         */
        private void increaseEnergy() {
            this.energy++;
        }

        /**
         * Decrement the energy of player.
         */
        private void decreaseEnergy() {
            this.energy--;
        }

        public int getEnergy() {
            return energy;
        }

        public boolean isOrbiting() {
            return isOrbiting;
        }

        public void setOrbiting(boolean isOrbiting) {
            this.isOrbiting = isOrbiting;
        }

        @Override
        public String toString() {
            return "[ENERGY: " + energy + "]";
        }
    }

    private static Player playerInstance;
    private static double orbitingTime;
    private final PlayerStat playerStat;
    private final List<AbstractItem> inventory; // inventory cache?

    private Player() {
        super();
        super.registerCharacterData();
        this.inventory = new ArrayList<>();
        this.playerStat = new PlayerStat();

        orbitingTime = 0d;
        position = super.getCharacterData().getPosition();
        velocity = super.getCharacterData().getVelocity();
        size = super.getCharacterData().getSize();
        collisionBox = super.getCharacterData().getCollisionBox();

        // player attributes.
        speed = super.getCharacterData().getSpeed();
        maxLife = super.getCharacterData().getMax_life();
        currentLife = maxLife;
        projectile = Optional.ofNullable(super.getCharacterData().getProjectile());
        projectileShootingCoolDownTimer = Optional.of(new Timer(60));
        healthBar = new HealthBar(maxLife);
        healthBar.setDrawHealthBar(true);
        shootPath = new ShootPath();

        sprite = getSprite();
    }

    /**
     * Configure a character data struct of this game character.
     *
     * @return a character data struct of this game character.
     */
    @Override
    protected CharacterData configCharacterData() {
        return Game.getConfigManager().getCharacter("player");
    }

    /**
     * Returns the single instance of {@code Player}. This method initializes the {@code Player} if the
     * instance is not being initialized. Otherwise, returns the instance of {@code Player}.
     *
     * @return the only instance of {@code Player}
     */
    public static Player getInstance() {
        if (playerInstance == null) {
            playerInstance = new Player();
        }
        return playerInstance;
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

        Vector2d oldPosition = position.copy();
        if (isMetMouseCursor()) {
            playerStat.setOrbiting(true);
        } else {
            position = oldPosition;
        }

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
        if (InputManager.isMouseMoved()) {
            playerStat.setOrbiting(false);
        }
        if (playerStat.isOrbiting()) {
            orbitAroundCursor(deltaTime);
        }
        // ===== UPDATE SHOOT PATH =====
        if (shootPath != null && InputManager.isButtonPressed(MouseInputListener.MouseButton.RIGHT_BUTTON)) {
            shootPath.update(position.add(
                    new Vector2d((double) this.size.getWidth() / 2,
                            (double) this.size.getHeight() / 2
                    )
            ));
        }
    }

    /**
     * Orbit the player around the mouse cursor. Trigger this condition only if the player character met the position
     * of mouse's cursor. The position of player's path is calculated by the following parametric equation:
     * <pre><code>
     *     x = cos(t) * r + mx - offsetX
     *     y = sin(t) * r + my - offsetY
     * </code></pre>
     * Symbols & their meanings:
     * <pre><code>
     *     x : x position of the player.
     *     y : y position of the player.
     *     t : time.
     *     r : orbiting radius.
     *     mx : mouse x position.
     *     my : mouse y position.
     *     offsetX : offset of the center of the circle in x axis.
     *     offsetY : offset of the center of the circle in y axis.
     * </code></pre>
     *
     * @param deltaTime a constant in game loop.
     */
    private void orbitAroundCursor(double deltaTime) {
        orbitingTime += deltaTime * 3;
        Vector2d mousePosition = new Vector2d(InputManager.getMousePosition());
        this.position = new Vector2d(
                Math.cos(orbitingTime) * PlayerStat.ORBIT_RADIUS + mousePosition.getX() - 16,
                Math.sin(orbitingTime) * PlayerStat.ORBIT_RADIUS + mousePosition.getY()
        );
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
     * Picks up a game item when player's collision box collides with game item's collision box.
     *
     * @param item that has been picked up by player.
     */
    public void pickUp(AbstractItem item) {
        if (item == null) return;
        inventory.add(item);
        if (item instanceof Fuel fuel) {
            currentLife += fuel.getLife();
            if (currentLife > maxLife) {
                currentLife = maxLife;
            }
        } else if (item instanceof Battery) {
            increaseEnergy();
        }
    }

    /**
     * Increment the energy of player.
     */
    public void increaseEnergy() {
        this.playerStat.increaseEnergy();
    }

    /**
     * Decrement the energy of player.
     */
    public void decreaseEnergy() {
        this.playerStat.decreaseEnergy();
    }

    /**
     * Check if the player currently can shoot projectiles or not. The player can shoot projectiles if and only if
     * the player's energy point (ep) is greater than 0.
     *
     * @return true if player has enough energy (ep > 0) and false otherwise.
     */
    public boolean canShoot() {
        return playerStat.getEnergy() > 0;
    }

    /**
     * Check if the player has met the mouse cursor's positon on screen.
     *
     * @return true if the player has met the mouse cursor's positon and false otherwise.
     */
    private boolean isMetMouseCursor() {
        Vector2d mousePosition = new Vector2d(InputManager.getMousePosition());
        double offset = speed / 2; // TODO find suitable value of offset.
        return this.position.compareWith(
                mousePosition.add(new Vector2d(offset, offset)),
                Vector.ComparisonCommand.LESS_THAN_OR_EQUALS_TO
        ) && this.position.compareWith(
                mousePosition.subtract(new Vector2d(offset, offset)),
                Vector.ComparisonCommand.GREATER_THAN_OR_EQUALS_TO
        );
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

    /**
     * Debugging method of development of this game. Prints player's statistics.
     */
    @DebugMethod(purpose = DebugMethod.DebugPurpose.PRINT_DEBUG_MESSAGE)
    public void printPlayerStat() {
        System.out.println(playerStat.toString());
    }

    /**
     * Debugging method of development of this game. Prints player's inventory.
     */
    @DebugMethod(purpose = DebugMethod.DebugPurpose.PRINT_DEBUG_MESSAGE)
    public void printInventory() {
        System.out.println(inventory.toString());
    }
}
