package david.game.graphics.components;

import david.game.entity.component.Size;
import david.game.graphics.auxiliary.Spacing;
import com.mrgoddavid.vector.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mr. GodDavid
 * @since 4/19/2026
 */
public abstract class UIComponent {

    protected Vector2i position;
    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    protected Image image;
    protected Color background;
    protected Color foreground;

    public UIComponent() {
        position = new Vector2i(10, 10);
        size = new Size(10, 10);
        margin = new Spacing(0);
        padding = new Spacing(0);

        background = Color.RED;
        foreground = Color.WHITE;
    }

    protected abstract Image getSprite();

    protected final Size calculateBackgroundImageSize() {
        return new Size(
                size.getWidth() + margin.getHorizontal() + padding.getHorizontal(),
                size.getHeight() + margin.getVertical() + padding.getVertical()
        );
    }

    protected final int calculateForegroundX() {
        return margin.getLeft() + padding.getLeft();
    }

    protected final int calculateForegroundY() {
        return margin.getTop() + padding.getTop();
    }

    public final Image getImage() {
        return (image == null) ? new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB) : image;
    }

    public final Vector2i getPosition() {
        return position;
    }
}
