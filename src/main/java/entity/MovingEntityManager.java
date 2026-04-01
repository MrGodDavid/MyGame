package entity;

import com.mrgoddavid.vector.Vector2d;
import entity.enemy.Enemy;
import entity.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all {@code MovingEntity} in this game.
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
public final class MovingEntityManager {

    private final List<MovingEntity> movingEntities;
    private static Player player;

    public MovingEntityManager() {
        movingEntities = new ArrayList<>();
        player = new Player();
        movingEntities.add(player);
    }

    public void update(double deltaTime) {
        addMovingEntitiesUpTo(10);

        Iterator<MovingEntity> iterator = movingEntities.iterator();
        while (iterator.hasNext()) {
            MovingEntity entity = iterator.next();
            entity.update(deltaTime);
            if (entity instanceof Enemy enemy) {
                if (enemy.isMetPlayer()) {
                    iterator.remove();
                }
            }
        }
    }

    private void addMovingEntitiesUpTo(int maxNumOfMovingEntities) {
        if (getNumOf(Enemy.class) <= maxNumOfMovingEntities) {
            for (int i = 0; i < maxNumOfMovingEntities; i++) {
                Enemy enemy = new Enemy();
                enemy.setPosition(new Vector2d(Math.random() * 400, Math.random() * 400));
                movingEntities.add(enemy);
            }
        }
    }

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

    public <T extends MovingEntity> int getNumOf(Class<T> filterClass) {
        return movingEntities.stream()
                .filter(filterClass::isInstance)
                .toList()
                .size();
    }

    public static Player getPlayer() {
        return player;
    }
}
