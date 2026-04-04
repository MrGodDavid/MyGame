package core.spatial_hash_grid;

import entity.MovingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
 *
 * @author Mr. GodDavid
 * @since 4/4/2026
 */
public final class SpatialHashGrid {

    private final int cellSize;
    private final Map<Cell, List<MovingEntity>> grid;

    private final List<MovingEntity> nearby;

    public SpatialHashGrid(int cellSize) {
        this.cellSize = cellSize;
        this.grid = new HashMap<>();
        this.nearby = new ArrayList<>();
    }

    private Cell getCell(double x, double y) {
        int cellX = (int) (x / cellSize);
        int cellY = (int) (y / cellSize);
        return new Cell(cellX, cellY);
    }

    public void clear() {
        grid.clear();
    }

    @SuppressWarnings("unused")
    public void insert(MovingEntity character) {
        Cell cell = getCell(character.getPosition().getX(), character.getPosition().getY());
        grid.computeIfAbsent(cell, k -> new ArrayList<>()).add(character);
    }

    public List<MovingEntity> getNearby(MovingEntity character) {
        nearby.clear();

        int cx = (int) (character.getPosition().getX() / cellSize);
        int cy = (int) (character.getPosition().getY() / cellSize);

        // checks neighbor 3x3 cells.
        for (int x = cx - 1; x <= cx + 1; x++) {
            for (int y = cy - 1; y <= cy + 1; y++) {
                Cell cell = new Cell(x, y);
                List<MovingEntity> list = grid.get(cell);
                if (list != null) {
                    nearby.addAll(list);
                }
            }
        }
        return nearby;
    }
}
