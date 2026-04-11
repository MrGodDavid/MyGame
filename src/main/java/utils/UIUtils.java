package utils;

import com.mrgoddavid.vector.Vector2i;
import entity.component.Size;

import java.awt.*;

/**
 * Custom math class.
 *
 * @author Mr. GodDavid
 * @since 4/11/2026
 */
public final class UIUtils {

    private static final int CORNER_ANGLE = 20;
    private static final float STROKE_THICKNESS = 2.0f;

    private static final Color BACKGROUND_COLOR = new Color(54, 54, 54);
    private static final Color STROKE_COLOR = new Color(225, 224, 224);

    private UIUtils() {
        throw new RuntimeException("You can't instantiate [UIUtils] class!");
    }

    public static void drawPanel(Graphics2D g2d, Vector2i position, Size size) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRoundRect(position.getX(), position.getY(), size.getWidth(), size.getHeight(), CORNER_ANGLE, CORNER_ANGLE);
        g2d.setColor(STROKE_COLOR);
        g2d.setStroke(new BasicStroke(STROKE_THICKNESS));
        g2d.drawRoundRect(position.getX(), position.getY(), size.getWidth(), size.getHeight(), CORNER_ANGLE, CORNER_ANGLE);
    }
}
