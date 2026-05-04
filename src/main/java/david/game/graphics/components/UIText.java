package david.game.graphics.components;

import com.mrgoddavid.vector.Vector2i;
import david.game.core.Game;
import david.game.entity.component.Size;
import david.game.utils.TextUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UI Text of this game. This UI component is a subclass of {@link UIComponent}.
 *
 * @author Mr. GodDavid
 * @since 4/24/2026
 */
public final class UIText extends UISmartComponent {

    private String text;

    public UIText(String text, Vector2i position, Size size) {
        super(position, size);
        this.text = text;
        this.position = position;
        this.size = size;

        image = getSprite();
    }

    @Override
    protected Image getSprite() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        Vector2i centeredPosition = TextUtils.getCenteredFontPosition(g2d, text, size);

        g2d.setFont(Game.getGameFont().deriveFont(Font.PLAIN, 24F));
        g2d.setColor(Color.WHITE);
        int y = centeredPosition.getY();
        int height = TextUtils.getTextHeight(g2d, text);
        for (String line : text.split("\n")) {
            g2d.drawString(line, 0, y);
            y += height;
        }
        g2d.dispose();

        return image;
    }

    /**
     * Draw a rectangle around the component via Graphics2D class.
     *
     * @param g2d acts as a rendering pipeline.
     */
    @Override
    public void drawBoundingBox(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(this.position.getX(), this.position.getY(), this.size.getWidth(), this.size.getHeight());
    }

    /**
     * Make sure that the SmartUIComponent is truly intentionally referencing the class that implements this
     * interface.
     *
     * @return the reference of the class that implements this interface.
     */
    @Override
    public UIComponent asUIComponent() {
        return this;
    }
}
