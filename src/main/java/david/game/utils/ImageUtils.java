package david.game.utils;

import bad.code.format.annotation.UninstantiableClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This utility class holds utility method of BufferedImage/Image class of this game.
 *
 * @author Mr. GodDavid
 * @since 5/3/2026
 */
@UninstantiableClass(reason = UninstantiableClass.Reason.UTILITY_CLASS)
public final class ImageUtils {

    private ImageUtils() {
        throw new RuntimeException("You can't instantiate this class.");
    }

    public static Image createImageThroughPath(String path) {
        try {
            File file = new File(path);
            return resize(ImageIO.read(file), 48, 48);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image resize(Image original, int targetWidth, int targetHeight) {
        Image resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) resized.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resized;
    }
}
