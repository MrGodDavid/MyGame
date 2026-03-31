package entity.component;

import com.mrgoddavid.vector.Vector2i;

/**
 * Size component of {@code GameObject}.
 * <p>
 * This class is a subclass of {@link Vector2i}.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Size extends Vector2i {

    public Size() {
        super();
    }

    public Size(int width, int height) {
        super(width, height);
    }

    public int getWidth() {
        return super.getX();
    }

    public int getHeight() {
        return super.getY();
    }
}
