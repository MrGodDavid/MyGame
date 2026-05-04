package david.game.graphics.components;

import com.mrgoddavid.vector.Vector2i;
import david.game.entity.component.Size;
import david.game.graphics.auxiliary.SmartUI;

/**
 * Smart UI Component that relocate its position based on the portion that specified by user in its constructor.
 *
 * @author Mr. GodDavid
 * @since 4/23/2026
 */
public abstract class UISmartComponent extends UIComponent implements SmartUI {

    private final double xPercentage;
    private final double yPercentage;

    public UISmartComponent(Vector2i position, Size size) {
        this(position, size, SmartUI.DISABLE_SMART_COMPONENT, SmartUI.DISABLE_SMART_COMPONENT);
    }

    public UISmartComponent(Vector2i position, Size size, double xPercentage, double yPercentage) {
        super();
        this.position = position;
        this.size = size;
        this.xPercentage = xPercentage;
        this.yPercentage = yPercentage;
    }

    @Override
    public double getSmartXPercentage() {
        return xPercentage;
    }

    @Override
    public double getSmartYPercentage() {
        return yPercentage;
    }
}
