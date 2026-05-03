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
    /**
     * The size of a UIComponent is the size excluded the margin and padding.
     */
    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    protected Image image;
    protected Color background;
    protected Color foreground;

    public UIComponent() {
        this(false);
    }

    public UIComponent(boolean drawBackground) {
        position = new Vector2i(10, 10);
        size = new Size(10, 10);
        margin = new Spacing(0);
        padding = new Spacing(0);

        background = drawBackground ? new Color(255, 0, 0) : new Color(0, 0, 0, 0);
        foreground = Color.WHITE;
    }

    protected abstract Image getSprite();

    /**
     * Draw a rectangle around the component via Graphics2D class.
     *
     * @param g2d acts as a rendering pipeline.
     */
    public abstract void drawBoundingBox(Graphics2D g2d);

    /**
     * Draw a rectangle around the component via Graphics2D class. This method should also calculate the absolute
     * position of a child UIComponent.
     *
     * @param g2d    acts as a rendering pipeline.
     * @param parent UIPanel/Container that might be null.
     */
    public void drawBoundingBox(Graphics2D g2d, UIComponent parent) {
        if (parent == null) return;
        g2d.setColor(Color.RED);
        g2d.drawRect(
                this.position.getX() + parent.getPosition().getX(),
                this.position.getY() + parent.getPosition().getY(),
                this.size.getWidth(),
                this.size.getHeight()
        );
    }

    /**
     * Calculate the size includes the margin and padding.
     *
     * @return the size includes the margin and padding.
     */
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

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public final Size getSize() {
        return size;
    }
}
