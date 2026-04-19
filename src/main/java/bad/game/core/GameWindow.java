package bad.game.core;

import bad.game.annotation.SingletonClass;
import bad.game.entity.component.Size;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

    private GameWindow(Game game) {
        window = new JFrame();
        window.setTitle("My Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(game);
        window.pack();
        windowSize = new Size(window.getWidth(), window.getHeight());
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                windowSize.setWidth(window.getWidth());
                windowSize.setHeight(window.getHeight());
            }
        });
//        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }

    public static GameWindow getInstance(Game game) {
        if (instance == null) {
            instance = new GameWindow(game);
        }
        return instance;
    }

    public void show() {
        window.setVisible(true);
    }

    public static Size getWindowSize() {
        return windowSize;
    }
}
