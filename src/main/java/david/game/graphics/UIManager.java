package david.game.graphics;

import bad.code.format.annotation.SingletonClass;
import com.mrgoddavid.vector.Vector2i;
import david.game.core.Game;
import david.game.entity.component.Size;
import david.game.graphics.auxiliary.SmartUI;
import david.game.graphics.components.UIComponent;
import david.game.graphics.components.UIPanel;
import david.game.graphics.components.UIText;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr. GodDavid
 * @since 4/15/2026
 */
@SingletonClass
public final class UIManager {

    private static class UIRenderingSetting {

        public static UIRenderingSetting instance;

        private boolean renderUIComponentBoundingBox;

        private UIRenderingSetting() {
            renderUIComponentBoundingBox = false;
        }

        public static UIRenderingSetting getInstance() {
            if (instance == null) {
                instance = new UIRenderingSetting();
            }
            return instance;
        }

        public void toggleRenderUIComponentBoundingBox() {
            renderUIComponentBoundingBox = !renderUIComponentBoundingBox;
        }

        public boolean isRenderUIComponentBoundingBox() {
            return renderUIComponentBoundingBox;
        }
    }

    // UI CACHES
    private static final List<UIComponent> playingStateUIComponentsCache = new ArrayList<>();
    private final List<UIComponent> editorStateUIComponentsCache;
    private final List<UIComponent> pauseStateUIComponentsCache;

    private static List<UIComponent> uiComponentRenderingList; // acts as a pointer.
    private static UIManager instance;
    private static UIRenderingSetting uiRenderingSetting;

    private UIManager() {
        this.editorStateUIComponentsCache = new ArrayList<>();
        this.pauseStateUIComponentsCache = new ArrayList<>();
        UIManager.uiComponentRenderingList = new ArrayList<>();

        uiRenderingSetting = UIRenderingSetting.getInstance();
        this.initialize();
    }

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    /**
     * Initialize all ui cache once before playing the game.
     */
    private void initialize() {
        pauseStateUIComponentsCache.add(drawPausePanel());
        playingStateUIComponentsCache.add(drawObjectivePanel());
    }

    private static UIPanel drawObjectivePanel() {
        final int PADDING = 50;
        final Size SIZE = new Size(192, 160);
        final Vector2i POSITION = new Vector2i(
                ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - SIZE.getWidth()) - PADDING,
                PADDING
        );
        UIPanel objectivePanel = new UIPanel(POSITION, SIZE);
        objectivePanel.addChild(
                new UIText(Game.getCurrentObjective().getTitle(),
                        new Vector2i(0, 0), new Size(128, 48)
                ),
                new UIText(Game.getCurrentObjective().getDescription(),
                        new Vector2i(0, 0), new Size(128, 96)
                )
        );
        return objectivePanel;
    }

    private UIPanel drawPausePanel() {
        final Size SIZE = new Size(256, 256);
        final Vector2i POSITION = new Vector2i(
                ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - SIZE.getWidth()) / 2,
                ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - SIZE.getHeight()) / 2
        );
        UIPanel pausePanel = new UIPanel(POSITION, SIZE, 0.5, 0.5);
        pausePanel.addChild(
                // The parameters are hard-coded. :)
                new UIText("PAUSE", new Vector2i(0, 0), new Size(60, 64)),
                new UIText("PRESS [P] to resume", new Vector2i(0, 0), new Size(192, 64)),
                new UIText("PRESS [ESC] to exit", new Vector2i(0, 0), new Size(192, 64))
        );
        return pausePanel;
    }

    public static void updateObjectivePanel() {
        playingStateUIComponentsCache.clear();
        if (Game.getCurrentObjective() != null) {
            playingStateUIComponentsCache.add(drawObjectivePanel());
        }
    }

    public void update() {
        switch (Game.getGameState()) {
            case PLAYING_STATE -> {
                uiComponentRenderingList = playingStateUIComponentsCache;
            }
            case EDITOR_STATE -> uiComponentRenderingList = editorStateUIComponentsCache;
            case PAUSE_STATE -> uiComponentRenderingList = pauseStateUIComponentsCache;
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
            if (uiRenderingSetting.isRenderUIComponentBoundingBox()) {
                uiComponent.drawBoundingBox(g2d);
            }
        }
    }

    /**
     * Reposition each ui component that maintains the x-scale and y-scale relative to the screen size.
     * <p>
     * Fire the {@link SmartUI#rePositioning()} when the user resizes game window.
     */
    public static void repositioningUIComponents() {
        for (UIComponent uiComponent : uiComponentRenderingList) {
            if (uiComponent instanceof SmartUI component) {
                component.rePositioning();
            }
        }
    }

    public static void toggleRenderUIComponentBoundingBox() {
        uiRenderingSetting.toggleRenderUIComponentBoundingBox();
    }
}
