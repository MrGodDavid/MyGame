package david.game.core.spatial_hash_grid;

import david.game.entity.GameObject;
import david.game.entity.MovingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===== Old Conventional Approach =====
 *
 * <p>
 * The core logic of the Spatial Hash Grid method is implemented in this class.
 * The main idea is to divide the game map into a grid made of cells.
 * Each cell contains {@code n} {@code MovingEntity} instances,
 * where {@code 0 <= n <= max_number_of_game_character}.
 * </p>
 *
 * <p>
 * When performing collision checks (for example, player vs. enemy),
 * the program only retrieves the cell that the player is currently in
 * and checks all {@code MovingEntity} instances within that cell and its neighboring cells.
 * All other entities in distant cells are ignored.
 * This significantly improves the efficiency of collision detection.
 * </p>
 *
 * <p>
 * This class contains an integer that defines the size of each cell,
 * and a map that uses cells as keys and lists of {@code MovingEntity} as values.
 * The class is initialized by providing the cell size.
 * </p>
 *
 * <p>
 * ===== Optimized Spatial Hash Grid =====
 * </p>
 *
 * <p>
 * Instead of using a dedicated Cell class as the key for {@code grid},
 * this implementation uses a custom hashed {@code long} value as the key.
 * </p>
 *
 * <p>
 * Hashing method: see {@link SpatialHashGrid#hash(int, int)}.
 * </p>
 *
 * @author Mr. GodDavid
 * @since 4/4/2026
 */
public final class SpatialHashGrid<T extends GameObject> {

    private final int cellSize;
    private final Map<Long, List<T>> grid;

    public SpatialHashGrid(int cellSize) {
        this.cellSize = cellSize;
        this.grid = new HashMap<>();
    }

    private int toCell(double coordinate) {
        return (int) (coordinate / cellSize);
    }

    /**
     * Hashing method: shift the x index of the grid 32 digits to the left, use bitwise and operation of y index of grid
     * and 0xffffffffL (in binary: 11111111 11111111 11111111 11111111), and use bitwise or operation to combine the
     * shifted x and combined y.
     *
     * @param x that is the coordinate of grid and must be an integer.
     * @param y that is the coordinate of grid and must be an integer.
     */
    private long hash(int x, int y) {
        return (((long) x) << 32) | (y & 0xffffffffL);
    }

    /**
     * Wrapper method of {@link Map#clear()}. Clear all key-element pairs in the grid.
     */
    public void clear() {
        grid.clear();
    }

    /**
     * Insert a {@code MovingEntity} to the Spatial Hash Grid. The key is determined by the position coordinate of the
     * {@code MovingEntity}.
     *
     * @param character that is not null.
     */
    @SuppressWarnings({"unused"})
    public void insert(T character) {
        int cx = toCell(character.getPosition().getX());
        int cy = toCell(character.getPosition().getY());

        long key = hash(cx, cy);

        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(character);
    }

    public void getNearby(MovingEntity character, List<T> nearby) {
        nearby.clear();

        int cx = (int) (character.getPosition().getX() / cellSize);
        int cy = (int) (character.getPosition().getY() / cellSize);

        // checks neighbor 3x3 cells.
        for (int x = cx - 1; x <= cx + 1; x++) {
            for (int y = cy - 1; y <= cy + 1; y++) {
                long key = hash(x, y);
                List<T> list = grid.get(key);
                if (list != null) {
                    nearby.addAll(list);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "The size of the Spatial Hash Grid is " + grid.size();
    }
}
