package david.game.graphics;

import bad.code.format.annotation.SingletonClass;
import david.game.entity.component.Size;
import david.game.graphics.components.UIComponent;
import com.mrgoddavid.vector.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SingletonClass
public final class UIManager {

    private static UIManager instance;
    private final List<UIComponent> uiComponents;

    private UIManager() {
        uiComponents = new ArrayList<>();
        this.initialize();
    }

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    private void initialize() {
        uiComponents.add(new UIPanel(
                new Vector2i(100, 100),
                new Size(100, 100)
        ));
    }

    public void update() {
//        for (UIComponent uiComponent : uiComponents) {
//
//        }
    }

    public void render(Graphics2D g2d) {
        for (UIComponent uiComponent : uiComponents) {
            g2d.drawImage(
                    uiComponent.getImage(),
                    uiComponent.getPosition().getX(),
                    uiComponent.getPosition().getY(),
                    null
            );
        }
    }
}
