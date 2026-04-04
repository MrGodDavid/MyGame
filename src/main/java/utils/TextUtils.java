package utils;

import com.mrgoddavid.vector.Vector2i;
import entity.component.Size;

import java.awt.*;

/**
 * Utility class for game texts.
 * <p>Get the width and height of the font text.</p>
 *
 * @author Mr. GodDavid
 * @since 3/31/2026
 */
public final class TextUtils {

    private TextUtils() {
        throw new RuntimeException("You can't instantiate class [TextUtils]");
    }

    public static int getTextWidth(Graphics2D g2d, String text) {
        return (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
    }

    public static int getTextHeight(Graphics2D g2d, String text) {
        return (int) g2d.getFontMetrics().getStringBounds(text, g2d).getHeight();
    }

    public static Vector2i getCenteredFontPosition(Graphics2D g2d, String text, Size contentSize) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int centerX = (contentSize.getWidth() - fontMetrics.stringWidth(text)) / 2;
        int centerY = (contentSize.getHeight() - fontMetrics.getAscent()) / 2;
        return new Vector2i(centerX, centerY);
    }
}
