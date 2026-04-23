package david.game.graphics;

import bad.code.format.annotation.SingletonClass;
import david.game.core.Game;
import david.game.core.GameWindow;
import david.game.entity.component.Size;
import david.game.graphics.auxiliary.SmartUIComponent;
import david.game.graphics.components.UIComponent;
import com.mrgoddavid.vector.Vector2i;
import david.game.graphics.components.UIPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SingletonClass
public final class UIManager {

    // UI CACHES
    private final List<UIComponent> editorStateUIComponentsCache;
    private final List<UIComponent> pauseStateUIComponentsCache;

    private static List<UIComponent> uiComponentRenderingList; // acts as a pointer.
    private static UIManager instance;

    private UIManager() {
        this.editorStateUIComponentsCache = new ArrayList<>();
        this.pauseStateUIComponentsCache = new ArrayList<>();

        UIManager.uiComponentRenderingList = new ArrayList<>();
        this.initialize();
    }

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    private void initialize() {
        editorStateUIComponentsCache.add(new UIPanel(
                new Vector2i(100, 100),
                new Size(100, 100)
        ));
        pauseStateUIComponentsCache.add(new UIPanel(
                new Vector2i(Game.GAME_WINDOW_SIZE.getWidth() / 2 - 50, Game.GAME_WINDOW_SIZE.getHeight() / 2 - 50),
                new Size(100, 100),
                0.5, 0.5
        ));
    }

    public void update() {
        switch (Game.getGameState()) {
            case EDITOR_STATE -> uiComponentRenderingList = editorStateUIComponentsCache;
            case PAUSE_STATE -> uiComponentRenderingList = pauseStateUIComponentsCache;
            case PLAYING_STATE -> uiComponentRenderingList = new ArrayList<>();
        }
    }

    public void render(Graphics2D g2d) {
        for (UIComponent uiComponent : uiComponentRenderingList) {
            g2d.drawImage(
                    uiComponent.getImage(),
                    uiComponent.getPosition().getX(),
                    uiComponent.getPosition().getY(),
                    null
            );
        }
    }

    public static void repositioningUIComponents() {
        for (UIComponent uiComponent : uiComponentRenderingList) {
            if (uiComponent instanceof SmartUIComponent component) {
                component.rePositioning();
            }
        }
    }
}
