package entity;

import com.mrgoddavid.vector.Vector2d;
import entity.enemy.Enemy;
import entity.player.Player;
import entity.projectile.Projectile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all {@code MovingEntity} in this game. This class contains a List of {@code MovingEntity} of this game.
 * <p>
 * This class first constructs the List of {@code MovingEntity} of this game. Secondly, this class initializes the
 * {@code Player} of the game and adds the {@code Player} to the List of {@code MovingEntity}.
 * <p>
 * This class contains an update method and a render method. The update method update all the {@code MovingEntity} inside
 * the List of {@code MovingEntity} of this game via {@link MovingEntity#update(double)}. The render method renders all
 * the {@code MovingEntity} by utilizing the {@link Graphics2D#drawImage(Image, int, int, ImageObserver)}. (In this case,
 * the {@link ImageObserver} is null.) The image parameters is the image got from {@link MovingEntity#getSprite()}.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
public final class MovingEntityManager {

    private static MovingEntityManager movingEntityManager;
    private static List<MovingEntity> movingEntities;
    private static Player player;

    private MovingEntityManager() {
        movingEntities = new ArrayList<>();
        player = Player.getInstance();
        movingEntities.add(player);
    }

    /**
     * Returns the single instance of {@code MovingEntityManager}. This method initializes the {@code MovingEntityManager} if the
     * instance is not being initialized. Otherwise, returns the instance of {@code MovingEntityManager}.
     *
     * @return the only instance of {@code MovingEntityManager}
     */
    public static MovingEntityManager getInstance() {
        if (movingEntityManager == null) {
            movingEntityManager = new MovingEntityManager();
        }
        return movingEntityManager;
    }

    /**
     * Update all {@code MovingEntity} of this game every frame.
     *
     * @param deltaTime that is used to update {@code MovingEntity} of this game.
     */
    public void update(double deltaTime) {
        addMovingEntitiesUpTo(10);
        updateAllMovingEntitiesInGame(deltaTime);
    }

    private void updateAllMovingEntitiesInGame(double deltaTime) {
        Iterator<MovingEntity> iterator = movingEntities.iterator();
        while (iterator.hasNext()) {
            MovingEntity entity = iterator.next();
            entity.update(deltaTime);
            if (entity instanceof Enemy enemy) {
                if (enemy.isMetPlayer()) {
//                    player.damage(1);
                    iterator.remove();
                }
            } else if (entity instanceof Projectile projectile) {
                if (!projectile.isAlive()) {
                    iterator.remove();
                }
            }
        }
    }

    private void addMovingEntitiesUpTo(int maxNumOfMovingEntities) {
        if (getNumOf(Enemy.class) < maxNumOfMovingEntities) {
            for (int i = 0; i < maxNumOfMovingEntities; i++) {
                Enemy enemy = new Enemy();
                enemy.setPosition(new Vector2d(Math.random() * 400, Math.random() * 400));
                movingEntities.add(enemy);
            }
        }
    }

    /**
     * Render all {@code MovingEntity} of this game every frame.
     *
     * @param g2d that can be considered as the graphics rendering pipeline that built inside {@link Graphics2D} class.
     */
    public void render(Graphics2D g2d) {
        for (int i = 0; i < movingEntities.size(); i++) {
            MovingEntity entity = movingEntities.get(i);
            g2d.drawImage(
                    entity.getSprite(),
                    (int) entity.getPosition().getX(),
                    (int) entity.getPosition().getY(),
                    null
            );
        }
    }

    /**
     * Utility method for finding how many {@code MovingEntity} that matches with the param {@code filterClass} inside
     * the List of {@code MovingEntity} of this game.
     *
     * @param filterClass that is the key for finding how many {@code MovingEntity} that matches the given
     *                    {@code filtering class.}
     * @param <T>         The type parameter that extends the {@link MovingEntity}.
     * @return the number of {@code MovingEntity} that matches with the {@code filtering class.}
     */
    public <T extends MovingEntity> int getNumOf(Class<T> filterClass) {
        return movingEntities.stream()
                .filter(filterClass::isInstance)
                .toList()
                .size();
    }

    public static void addMovingEntity(MovingEntity entity) {
        movingEntities.add(entity);
    }

    public static Player getPlayer() {
        return player;
    }
}
