package david.game.graphics;

import david.game.entity.component.Size;
import david.game.graphics.components.UIComponent;
import com.mrgoddavid.vector.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mr. GodDavid
 * @since 4/20/2026
 */
public final class UIPanel extends UIComponent {

    private static final int CORNER_ANGLE = 20;
    private static final float STROKE_THICKNESS = 2.0f;
    private static final Color BACKGROUND_COLOR = new Color(54, 54, 54);
    private static final Color STROKE_COLOR = new Color(225, 224, 224);

    public UIPanel(Vector2i position, Size size) {
        super();
        this.position = position;
        this.size = size;

        image = getSprite();
    }

    @Override
    protected Image getSprite() {
        BufferedImage backgroundImage = new BufferedImage(
                super.calculateBackgroundImageSize().getWidth(),
                super.calculateBackgroundImageSize().getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        BufferedImage foregroundImage = new BufferedImage(
                size.getWidth(),
                size.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d_background = backgroundImage.createGraphics();
        Graphics2D g2d_foreground = foregroundImage.createGraphics();

        this.drawPanel(g2d_foreground, size);
        g2d_foreground.dispose();

        g2d_background.setColor(background);
        g2d_background.fillRect(
                0, 0,
                super.calculateBackgroundImageSize().getWidth(),
                super.calculateBackgroundImageSize().getHeight()
        );
        g2d_background.drawImage(
                foregroundImage,
                super.calculateForegroundX(),
                super.calculateForegroundY(),
                null
        );
        g2d_background.dispose();

        return backgroundImage;
    }

    private void drawPanel(Graphics2D g2d, Size size) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRoundRect(0, 0, size.getWidth(), size.getHeight(), CORNER_ANGLE, CORNER_ANGLE);
        g2d.setColor(STROKE_COLOR);
        g2d.setStroke(new BasicStroke(STROKE_THICKNESS));
        g2d.drawRoundRect(0, 0, size.getWidth(), size.getHeight(), CORNER_ANGLE, CORNER_ANGLE);
    }
}
