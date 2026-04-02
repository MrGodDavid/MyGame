package entity.component;

import com.mrgoddavid.vector.Vector2i;

/**
 * Size component of {@code GameObject}.
 * <p>
 * This class is a subclass of {@link Vector2i}. It stores the {@code width} and {@code height} to Vector2i class.
 * This class utilizes the {@code getWidth()} which is the wrapper method of {@link Vector2i#getX()} to get the
 * x-component of {@code Vector2i}; and utilizes the {@code getHeight()} which is the wrapper method of
 * {@link Vector2i#getY()} to get the y-component of  {@code Vector2i}.
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

    @Override
    public String toString() {
        return "Size: width=" + this.getWidth() + ", height=" + this.getHeight();
    }
}
