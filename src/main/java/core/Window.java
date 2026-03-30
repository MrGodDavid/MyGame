package core;

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

    public Window() {
        window = new JFrame();
        window.setTitle("My Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addDisplayComponents();

        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setFocusable(false);
    }

    private void addDisplayComponents() {
        Canvas canvas = new Canvas();

        canvas.setFocusable(true);
        canvas.setPreferredSize(new Dimension(800, 600));
        canvas.setBackground(Color.BLACK);

        window.add(canvas);
    }

    public void show() {
        window.setVisible(true);
    }
}
