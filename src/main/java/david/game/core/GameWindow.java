package david.game.core;

import bad.code.format.annotation.SingletonClass;
import david.game.entity.component.Size;

import javax.swing.*;

/**
 * Create a window for this game.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
@SingletonClass
public final class GameWindow {

    private static GameWindow instance;
    private static Size windowSize;

    private final JFrame window;
    private final GameWindowResizeListener resizeListener;

    private GameWindow(Game game) {
        window = new JFrame();
        window.setTitle("My Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resizeListener = GameWindowResizeListener.getInstance();

        window.add(game);
        window.pack();
        windowSize = new Size(window.getWidth(), window.getHeight());
        window.addComponentListener(resizeListener);
//        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }

    public static GameWindow getInstance(Game game) {
        if (instance == null) {
            instance = new GameWindow(game);
        }
        return instance;
    }

    public static void updateWindowSize(int width, int height) {
        windowSize.setWidth(width);
        windowSize.setHeight(height);
    }

    public void show() {
        window.setVisible(true);
    }

    public static Size getWindowSize() {
        return windowSize;
    }
}
