package core.spatial_hash_grid;

import entity.GameCharacter;
import entity.GameObject;
import entity.MovingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===== Old conventional approach =====
 * <p>
 * The core logic of Spatial Hash Grid method is in this class. The mean idea is dividing the game map down into
 * grid made of cell. Each cell contains n {@code MovingEntity} (where 0 <= n <= max_number_of_game_character).
 * <p>
 * When checking the collision (for example, the player to enemy), the program only get the cell that the player is in,
 * and checks the all the {@code MovingEntity} within all the neighboring cells. The program thus ignores the rest of
 * {@code MovingEntity} in other cells. In this way, the program can efficiently check collision between each
 * {@code MovingEntity}.
 * <p>
 * This class contains an integer that defines the size of each cell, and a Map that has cell as key and a list of
 * {@code MovingEntity} as value. Initialize this class by sending the size of the cell.
 * <p>
 * ===== Optimized Spatial hash Grid =====
 * Instead of creating a Cell class as key for {@code grid}, we define a custom hash Long wrapper class for the key.
 * Hashing method: See {@link SpatialHashGrid#hash(int, int)}.
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
