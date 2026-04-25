package david.game.graphics.auxiliary;

import com.mrgoddavid.vector.Vector2i;
import david.game.core.GameWindow;
import david.game.entity.component.Size;
import david.game.graphics.components.UIComponent;

/**
 * @author Mr. GodDavid
 * @since 4/22/2026
 */
public interface SmartUI {

    /**
     * Disables UI Component automatically adjusting its position in {@code x-percentage} and {@code y-percentage}
     * relative to the total screen size.
     */
    int DISABLE_SMART_COMPONENT = -1;

    double getSmartXPercentage();

    double getSmartYPercentage();

    /**
     * Make sure that the SmartUIComponent is truly intentionally referencing the class that implements this
     * interface.
     *
     * @return the reference of the class that implements this interface.
     */
    UIComponent asUIComponent();

    default void rePositioning() {
        UIComponent self = asUIComponent();
        Size size = self.getSize();

        if (getSmartXPercentage() > 0d && getSmartYPercentage() > 0d) {
            self.setPosition(new Vector2i(
                    (int) (GameWindow.getWindowSize().getWidth() * ((getSmartXPercentage() > 0d) ? getSmartXPercentage() : 1d)
                            - (double) size.getWidth() / 2),
                    (int) (GameWindow.getWindowSize().getHeight() * ((getSmartXPercentage() > 0d) ? getSmartYPercentage() : 1d)
                            - (double) size.getHeight() / 2)
            ));
        }
    }
}
