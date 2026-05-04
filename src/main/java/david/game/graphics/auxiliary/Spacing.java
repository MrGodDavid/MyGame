package david.game.graphics.auxiliary;

/**
 * Spacing class indicates the UI component's body's left, up, right, and bottom spacing. In this game, spacing is used
 * to define the left, top, right, and bottom margin and padding.
 *
 * @author Mr. GodDavid
 * @since 4/19/2026
 */
@SuppressWarnings({"unused", "ClassCanBeRecord"})
public final class Spacing {

    private final int left, top, right, bottom;

    public Spacing(int spacing) {
        this(spacing, spacing);
    }

    public Spacing(int horizontal, int vertical) {
        this(horizontal, vertical, horizontal, vertical);
    }

    public Spacing(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getHorizontal() {
        return left + right;
    }

    public int getVertical() {
        return top + bottom;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }
}
