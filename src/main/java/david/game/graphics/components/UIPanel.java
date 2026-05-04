package david.game.graphics.components;

import david.game.entity.component.Size;
import com.mrgoddavid.vector.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * UI Panel component. UI Panel is a panel that can hold multiple UI Components. It is a subclass of smart ui component.
 *
 * @author Mr. GodDavid
 * @since 4/20/2026
 */
public final class UIPanel extends UISmartComponent {

    private static final int CORNER_ANGLE = 20;
    private static final float STROKE_THICKNESS = 2.0f;
    private static final Color BACKGROUND_COLOR = new Color(54, 54, 54);
    private static final Color STROKE_COLOR = new Color(225, 224, 224);

    private final List<UIComponent> children;
    private boolean updateCalledOnce;

    public UIPanel(Vector2i position, Size size) {
        this(position, size, -1d, -1d);
    }

    public UIPanel(Vector2i position, Size size, double xPercentage, double yPercentage) {
        super(position, size, xPercentage, yPercentage);
        this.children = new ArrayList<>();
        this.updateCalledOnce = false;
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

    /**
     * Draw a rectangle around the component via Graphics2D class.
     *
     * @param g2d acts as a rendering pipeline.
     */
    @Override
    public void drawBoundingBox(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.drawRect(this.position.getX(), this.position.getY(), this.size.getWidth(), this.size.getHeight());
        for (UIComponent uiComponent : children) {
            uiComponent.drawBoundingBox(g2d, this);
        }
    }

    private void drawPanel(Graphics2D g2d, Size size) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRoundRect(0, 0, size.getWidth(), size.getHeight(), CORNER_ANGLE, CORNER_ANGLE);
        g2d.setColor(STROKE_COLOR);
        g2d.setStroke(new BasicStroke(STROKE_THICKNESS));
        g2d.drawRoundRect(0, 0, size.getWidth(), size.getHeight(), CORNER_ANGLE, CORNER_ANGLE);
    }

    /**
     * Adds all children components in this UI panel.
     *
     * @param child components that this container has.
     * @apiNote Use this method {@code ONCE}. Add all children to panel through this method.
     */
    public void addChild(UIComponent... child) {
        if (updateCalledOnce) {
            System.out.println("[WARNING] in method {UIPanel.addChild()} because using " +
                    "method addChild method more than once.");
            System.out.println("[Bad method usage] in method {UIPanel.addChild()}. addChild does nothing because" +
                    "using this method more than once. See api note.");
            return;
        }
        updateCalledOnce = true;
        this.children.addAll(List.of(child));
        updateImage();
    }

    private void updateImage() {
        Graphics2D g2d = ((BufferedImage) image).createGraphics();
        int totalChildrenHeight = calculateTotalChildrenHeight();
        int verticalOffset = (super.calculateBackgroundImageSize().getHeight() - totalChildrenHeight) / 2;
        int currentX;
        int currentY = verticalOffset;
        for (UIComponent child : this.children) {
            currentX = (super.calculateBackgroundImageSize().getWidth() - child.getSize().getWidth()) / 2;
            child.setPosition(new Vector2i(currentX, currentY));
            g2d.drawImage(
                    child.getImage(),
                    child.getPosition().getX(), // NOTE: The child's position X is the relative position to the parent panel.
                    child.getPosition().getY(), // NOTE: The child's position Y is the relative position to the parent panel.
                    null
            );
            currentY += child.getImage().getHeight(null);
        }
    }

    private int calculateTotalChildrenHeight() {
        int totalHeight = 0;
        for (UIComponent child : this.children) {
            totalHeight += child.calculateBackgroundImageSize().getHeight();
        }
        return totalHeight;
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
