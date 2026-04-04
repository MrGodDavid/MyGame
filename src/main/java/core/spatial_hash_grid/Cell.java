package core.spatial_hash_grid;

/**
 * This is the cell class for Spatial Hash Grid.
 *
 * @author Mr. GodDavid
 * @since 4/4/2026
 */
public final class Cell {

    private int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        // If this and other both share the same memory address, returns true.
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
