package bad.game.graphics;

import bad.game.annotation.SingletonClass;
import bad.game.graphics.components.UIComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SingletonClass
public final class UIManager {

    private static UIManager instance;

    private final List<UIComponent> uiComponents;

    private UIManager() {
        uiComponents = new ArrayList<>();
    }

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    public void add(UIComponent uiComponent) {
        uiComponents.add(uiComponent);
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
