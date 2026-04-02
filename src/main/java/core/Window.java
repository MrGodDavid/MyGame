package core;

import game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Create a window for this game.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Window {

    private final JFrame window;

    public Window(Game game) {
        window = new JFrame();
        window.setTitle("My Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(game);

        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
//        window.setFocusable(false);
    }

    public void show() {
        window.setVisible(true);
    }
}
