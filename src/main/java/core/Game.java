package core;

import entity.MovingEntityManager;
import entity.component.Size;
import input.InputManager;
import input.KeyboardListener;
import input.MouseInputListener;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * The actual game reference of this project.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Game extends JPanel {

    public static final Size GAME_WINDOW_SIZE = new Size(800, 600);
    private static Game instance;

    private final InputManager inputManager;
    private final MovingEntityManager movingEntityManager;

    private static Font font_m6x11plus;

    private Game() {
        Game.font_m6x11plus = createFont("/font/m6x11plus.ttf");

        final KeyboardListener keyboardListener = KeyboardListener.getInstance();
        final MouseInputListener mouseInputListener = MouseInputListener.getInstance();
        this.inputManager = InputManager.getInstance(keyboardListener, mouseInputListener);
        this.movingEntityManager = MovingEntityManager.getInstance();

        super.setPreferredSize(new Dimension(GAME_WINDOW_SIZE.getWidth(), GAME_WINDOW_SIZE.getHeight()));
        super.setDoubleBuffered(true);
        super.setFocusable(true);

        super.addKeyListener(keyboardListener);
        super.addMouseListener(mouseInputListener);
        super.addMouseMotionListener(mouseInputListener);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void update(double deltaTime) {
        inputManager.update(deltaTime);
        movingEntityManager.update(deltaTime);
    }

    public void render() {
        super.repaint();
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation you must honor the opaque property, that is
     * if this component is opaque, you must completely fill in the background
     * in an opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see ComponentUI
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        movingEntityManager.render(g2d);
    }

    private Font createFont(final String filePath) {
        InputStream iS = Game.class.getResourceAsStream(filePath);
        if (iS == null) return null;
        try {
            return Font.createFont(Font.TRUETYPE_FONT, iS);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("ERROR: Could not find font through file path [" + filePath + "]");
        }
    }

    public static Font getGameFont() {
        return Game.font_m6x11plus;
    }
}
