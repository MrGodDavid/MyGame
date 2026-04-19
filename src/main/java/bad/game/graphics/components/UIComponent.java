package bad.game.graphics.components;

import bad.game.entity.component.Size;
import bad.game.graphics.auxiliary.Spacing;
import com.mrgoddavid.vector.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mr. GodDavid
 * @since 4/19/2026
 */
public class UIComponent {

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
        image = getSprite();
    }

    protected Image getSprite() {
        BufferedImage backgroundImage = new BufferedImage(
                size.getWidth() + margin.getHorizontal() + padding.getHorizontal(),
                size.getHeight() + margin.getVertical() + padding.getVertical(),
                BufferedImage.TYPE_INT_ARGB
        );
        BufferedImage foregroundImage = new BufferedImage(
                size.getWidth(),
                size.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d_background = backgroundImage.createGraphics();
        Graphics2D g2d_foreground = foregroundImage.createGraphics();

        g2d_foreground.setColor(foreground);
        g2d_foreground.fillRect(0, 0, size.getWidth(), size.getHeight());
        g2d_foreground.dispose();

        g2d_background.setColor(background);
        g2d_background.fillRect(
                0, 0,
                size.getWidth() + margin.getHorizontal() + padding.getHorizontal(),
                size.getHeight() + margin.getVertical() + padding.getVertical()
        );
        g2d_background.drawImage(
                foregroundImage,
                margin.getLeft() + padding.getLeft(),
                margin.getTop() + padding.getTop(),
                null
        );
        g2d_background.dispose();

        return backgroundImage;
    }

    public Image getImage() {
        return image;
    }

    public Vector2i getPosition() {
        return position;
    }
}
