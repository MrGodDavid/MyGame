package entity.component;

import com.mrgoddavid.vectorMath.Vector2d;

public final class Size extends Vector2d {

    public Size() {
        super();
    }

    public Size(int width, int height) {
        super(width, height);
    }

    public int getWidth() {
        return (int) getX();
    }

    public int getHeight() {
        return (int) getY();
    }
}
