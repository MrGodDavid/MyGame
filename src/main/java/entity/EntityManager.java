package entity;

import com.mrgoddavid.vector.Vector2d;
import core.spatial_hash_grid.SpatialHashGrid;
import entity.enemy.Enemy;
import entity.item.AbstractItem;
import entity.item.fuel.Fuel;
import entity.player.Player;
import entity.projectile.Projectile;
import utils.Math;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
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
public final class EntityManager {

    private static EntityManager instance;
    private static Player player;
    private static List<GameCharacter> gameCharacters;
    private static List<Projectile> projectiles;
    private static List<AbstractItem> items;

    private final SpatialHashGrid<MovingEntity> movingEntitySpatialHashGrid;
    private final SpatialHashGrid<AbstractItem> abstractItemSpatialHashGrid;
    private final List<MovingEntity> movingEntityQuarryBuffer;
    private final List<AbstractItem> abstractItemQuarryBuffer;

    private boolean added;

    private EntityManager() {
        gameCharacters = new ArrayList<>();
        projectiles = new ArrayList<>();
        items = new ArrayList<>();

        movingEntitySpatialHashGrid = new SpatialHashGrid<>(128);
        abstractItemSpatialHashGrid = new SpatialHashGrid<>(128);
        movingEntityQuarryBuffer = new ArrayList<>();
        abstractItemQuarryBuffer = new ArrayList<>();

        player = Player.getInstance();
        gameCharacters.add(player);

        added = false;
    }

    /**
     * Returns the single instance of {@code MovingEntityManager}. This method initializes the {@code MovingEntityManager} if the
     * instance is not being initialized. Otherwise, returns the instance of {@code MovingEntityManager}.
     *
     * @return the only instance of {@code MovingEntityManager}
     */
    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    /**
     * Update all {@code GameCharacter} of this game every frame.
     *
     * @param deltaTime that is used to update {@code GameCharacter} of this game.
     */
    public void update(double deltaTime) {

        addMovingEntitiesUpTo(1);
        addItemsUpTo(10);

        updateAllGameCharactersInGame(deltaTime);
        updateAllProjectilesInaGame(deltaTime);
        updateAllItemsInGame(deltaTime);

        movingEntitySpatialHashGrid.clear();
        abstractItemSpatialHashGrid.clear();

        insertAllMovingEntitiesToSpatialHashGrid();
        insertAllItemsToSpatialHashGrid();

        handleProjectileCollisions();
        handlePlayerToEnemyCollisions();
        handlePlayerToItemCollisions();

        cleanupSpatialHashGrid();
    }

    private void cleanupSpatialHashGrid() {
        gameCharacters.removeIf(character -> character instanceof Enemy && !character.isAlive());
        projectiles.removeIf(projectile -> !projectile.isAlive());
        items.removeIf(item -> !item.isDraw());
    }

    private void handlePlayerToItemCollisions() {
        abstractItemSpatialHashGrid.getNearby(player, abstractItemQuarryBuffer);
        for (AbstractItem item : abstractItemQuarryBuffer) {
            if (item.isCollidingWith(player.getCollisionBox())) {
                item.notDrawing();
                player.pickUp(item);
            }
        }
    }

    private void handlePlayerToEnemyCollisions() {
        movingEntitySpatialHashGrid.getNearby(player, movingEntityQuarryBuffer);
        for (MovingEntity movingEntity : movingEntityQuarryBuffer) {
            if (movingEntity instanceof Enemy enemy) {
                if (enemy.isCollidingWith(player.getCollisionBox())) {
                    enemy.setCurrentLife(0);
                }
                enemy.shoot();
            }
        }
    }

    private void handleProjectileCollisions() {
        for (Projectile projectile : projectiles) {
            movingEntitySpatialHashGrid.getNearby(projectile, movingEntityQuarryBuffer);
            for (MovingEntity movingEntity : movingEntityQuarryBuffer) {
                if (projectile.getShooter() != null
                        && !(projectile.getShooter() instanceof Enemy)
                        && movingEntity instanceof Enemy enemy
                ) {
                    if (enemy.isCollidingWith(projectile.getCollisionBox())) {
                        enemy.damage(1);
                        projectile.setCurrentLife(0);
                    }
                }
            }
        }
    }

    private void insertAllItemsToSpatialHashGrid() {
        for (AbstractItem item : items) {
            abstractItemSpatialHashGrid.insert(item);
        }
    }

    private void insertAllMovingEntitiesToSpatialHashGrid() {
        for (GameCharacter gameCharacter : gameCharacters) {
            movingEntitySpatialHashGrid.insert(gameCharacter);
        }
        for (Projectile projectile : projectiles) {
            movingEntitySpatialHashGrid.insert(projectile);
        }
    }

    private void updateAllItemsInGame(double deltaTime) {
        for (AbstractItem item : items) {
            item.update(deltaTime);
        }
    }

    private void updateAllGameCharactersInGame(double deltaTime) {
        for (GameCharacter entity : gameCharacters) {
            entity.update(deltaTime);
        }
    }

    private void updateAllProjectilesInaGame(double deltaTime) {
        for (Projectile projectile : projectiles) {
            projectile.update(deltaTime);
        }
    }

    private void addItemsUpTo(int maxNum) {
        int currentNum = getNumOfItem(AbstractItem.class);
        int addTo = maxNum - currentNum;
        if (currentNum <= maxNum) {
            for (int i = 0; i < addTo; i++) {
                items.add(new Fuel(Math.getRandomPositionOnScreen()));
            }
        }
    }

    private void addMovingEntitiesUpTo(int maxNumOfMovingEntities) {
        int currentNumOfEntities = getNumOfCharacter(Enemy.class);
        int addTo = maxNumOfMovingEntities - currentNumOfEntities;
        if (currentNumOfEntities < maxNumOfMovingEntities) {
            for (int i = 0; i < addTo; i++) {
                Enemy enemy = new Enemy();
                enemy.setPosition(new Vector2d(300, 300));
                gameCharacters.add(enemy);
            }
        }
    }

    /**
     * Render all {@code MovingEntity} of this game every frame.
     *
     * @param g2d that can be considered as the graphics rendering pipeline that built inside {@link Graphics2D} class.
     */
    public void render(Graphics2D g2d) {
        renderAllGameCharacters(g2d);
        renderAllProjectiles(g2d);
        renderAllItemsOnScreen(g2d);
    }

    private void renderAllItemsOnScreen(Graphics2D g2d) {
        for (AbstractItem item : items) {
            if (item.isDraw() && item.inCamera()) {
                g2d.drawImage(
                        item.getSprite(),
                        (int) item.getPosition().getX(),
                        (int) item.getPosition().getY(),
                        null
                );
            }
        }
    }

    private void renderAllProjectiles(Graphics2D g2d) {
        for (Projectile projectile : projectiles) {
            if (projectile.inCamera() && projectile.isAlive()) {
                g2d.drawImage(
                        projectile.getSprite(),
                        (int) projectile.getPosition().getX(),
                        (int) projectile.getPosition().getY(),
                        null
                );
            }
        }
    }

    private void renderAllGameCharacters(Graphics2D g2d) {
        for (GameCharacter character : gameCharacters) {
            if (character.inCamera()) {
                g2d.drawImage(
                        character.getSprite(),
                        (int) character.getPosition().getX(),
                        (int) character.getPosition().getY(),
                        null
                );
                character.render(g2d);
            }
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
    public <T extends GameCharacter> int getNumOfCharacter(Class<T> filterClass) {
        return gameCharacters.stream()
                .filter(filterClass::isInstance)
                .toList()
                .size();
    }

    public <T extends AbstractItem> int getNumOfItem(Class<T> filterClass) {
        return items.stream()
                .filter(filterClass::isInstance)
                .toList()
                .size();
    }

    public static void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public static Player getPlayer() {
        return player;
    }

    public static <T extends GameCharacter> List<GameCharacter> getAll(Class<T> filterClass) {
        return gameCharacters.stream()
                .filter(filterClass::isInstance)
                .toList();
    }
}
