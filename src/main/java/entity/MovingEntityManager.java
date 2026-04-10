package entity;

import com.mrgoddavid.vector.Vector2d;
import core.spatial_hash_grid.SpatialHashGrid;
import entity.enemy.Enemy;
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
public final class MovingEntityManager {

    private static MovingEntityManager instance;
    private static List<GameCharacter> gameCharacters;
    private static List<Projectile> projectiles;
    private static Player player;

    private final SpatialHashGrid spatialHashGrid;
    private final List<MovingEntity> queryBuffer;

    private MovingEntityManager() {
        gameCharacters = new ArrayList<>();
        projectiles = new ArrayList<>();
        spatialHashGrid = new SpatialHashGrid(128);
        queryBuffer = new ArrayList<>();

        player = Player.getInstance();
        gameCharacters.add(player);
    }

    /**
     * Returns the single instance of {@code MovingEntityManager}. This method initializes the {@code MovingEntityManager} if the
     * instance is not being initialized. Otherwise, returns the instance of {@code MovingEntityManager}.
     *
     * @return the only instance of {@code MovingEntityManager}
     */
    public static MovingEntityManager getInstance() {
        if (instance == null) {
            instance = new MovingEntityManager();
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

        updateAllGameCharactersInGame(deltaTime);
        updateAllProjectilesInaGame(deltaTime);

        spatialHashGrid.clear();

        insertAllMovingEntitiesToSpatialHashGrid();
        handleProjectileCollisions();
        handlePlayerToEnemyCollisions();

        cleanupSpatialHashGrid();
    }

    private void cleanupSpatialHashGrid() {
        gameCharacters.removeIf(character -> character instanceof Enemy && !character.isAlive());
        projectiles.removeIf(projectile -> !projectile.isAlive());
    }

    private void handlePlayerToEnemyCollisions() {
        spatialHashGrid.getNearby(player, queryBuffer);
        for (MovingEntity movingEntity : queryBuffer) {
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
            spatialHashGrid.getNearby(projectile, queryBuffer);
            for (MovingEntity movingEntity : queryBuffer) {
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

    private void insertAllMovingEntitiesToSpatialHashGrid() {
        for (GameCharacter gameCharacter : gameCharacters) {
            spatialHashGrid.insert(gameCharacter);
        }
        for (Projectile projectile : projectiles) {
            spatialHashGrid.insert(projectile);
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

    private void addMovingEntitiesUpTo(int maxNumOfMovingEntities) {
        int currentNumOfEntities = getNumOf(Enemy.class);
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
        for (int i = 0; i < gameCharacters.size(); i++) {
            GameCharacter character = gameCharacters.get(i);
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

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
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

    /**
     * Utility method for finding how many {@code MovingEntity} that matches with the param {@code filterClass} inside
     * the List of {@code MovingEntity} of this game.
     *
     * @param filterClass that is the key for finding how many {@code MovingEntity} that matches the given
     *                    {@code filtering class.}
     * @param <T>         The type parameter that extends the {@link MovingEntity}.
     * @return the number of {@code MovingEntity} that matches with the {@code filtering class.}
     */
    public <T extends GameCharacter> int getNumOf(Class<T> filterClass) {
        return gameCharacters.stream()
                .filter(filterClass::isInstance)
                .toList()
                .size();
    }

    public static void addMovingEntity(GameCharacter entity) {
        gameCharacters.add(entity);
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

    public static List<GameCharacter> getGameCharacters() {
        return gameCharacters;
    }
}
