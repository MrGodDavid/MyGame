package game;

import entity.GameObject;
import entity.player.Player;
import input.InputManager;
import input.KeyboardListener;
import input.MouseInputListener;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The actual game reference of this project.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class Game extends JPanel {

    private final InputManager inputManager;
    private final List<GameObject> gameObjects;

    private static Font font_m6x11plus;

    public Game() {
        Game.font_m6x11plus = createFont("/font/m6x11plus.ttf");

        final KeyboardListener keyboardListener = new KeyboardListener();
        final MouseInputListener mouseInputListener = new MouseInputListener();
        this.inputManager = new InputManager(keyboardListener, mouseInputListener);

        super.setPreferredSize(new Dimension(800, 600));
        super.setDoubleBuffered(true);
        super.setFocusable(true);

        super.addKeyListener(keyboardListener);
        super.addMouseListener(mouseInputListener);
        super.addMouseMotionListener(mouseInputListener);

        this.gameObjects = new ArrayList<>();
        gameObjects.add(new Player());
    }

    public void update(double deltaTime) {
        inputManager.update(deltaTime);

        gameObjects.forEach(gameObject -> gameObject.update(deltaTime));
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

        for (GameObject gameObject : gameObjects) {
            g2d.drawImage(
                    gameObject.getSpriteImage(),
                    (int) gameObject.getPosition().getX(),
                    (int) gameObject.getPosition().getY(),
                    null
            );
        }
    }

    public Font createFont(final String filePath) {
        InputStream iS = Game.class.getResourceAsStream(filePath);
        try {
            Font font =  Font.createFont(Font.TRUETYPE_FONT, iS);

            return font;
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("ERROR: Could not find font through file path [" + filePath + "]");
        }
    }

    public static Font getGameFont() {
        return Game.font_m6x11plus;
    }
}
