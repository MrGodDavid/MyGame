package david.game.core;

import bad.code.format.annotation.SingletonClass;
import david.game.data.gus.ConfigManager;
import david.game.entity.EntityManager;
import david.game.entity.component.Size;
import david.game.graphics.UIManager;
import david.game.input.InputManager;
import david.game.input.KeyboardListener;
import david.game.input.MouseInputListener;
import david.game.quest.QuestManager;
import david.game.quest.objective.Objective;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * The actual game reference of this project.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
@SingletonClass
public final class Game extends JPanel {

    private static class GameSettings {

        private boolean pause;
        private boolean debugMode;

        private GameSettings() {
            this.pause = true;
            this.debugMode = false;
        }

        private boolean isGamePause() {
            return pause;
        }

        private boolean isDebugMode() {
            return debugMode;
        }

        /**
         * Toggle the boolean flag {@code toggleUpdateGame}.
         * Method code:
         * <pre>
         * private void toggleUpdateGame() {
         *     updateGame = !updateGame;
         * }
         * </pre>
         */
        private void toggleUpdateGame() {
            pause = !pause;
        }

        private void toggleDebugMode() {
            debugMode = !debugMode;
        }
    }

    // WINDOW SIZE
    public static final int UNIT_LENGTH = 64;
    public static final int NUM_OF_TILE_WIDTH = 16;
    public static final int NUM_OF_TILE_HEIGHT = 12;
    public static final int WINDOW_WIDTH = UNIT_LENGTH * NUM_OF_TILE_WIDTH; // 64 * 16 = 1024 px
    public static final int WINDOW_HEIGHT = UNIT_LENGTH * NUM_OF_TILE_HEIGHT; // 64 * 12 = 768 px
    public static final Size GAME_WINDOW_SIZE = new Size(WINDOW_WIDTH, WINDOW_HEIGHT);

    // GAME ATTRIBUTES
    private static Random randomGenerator;
    private static Game instance;
    private static Font font_m6x11plus;
    private static GameSettings gameSettings;
    private static GameState gameState;

    // MANAGERS
    private static final ConfigManager configManager = ConfigManager.getInstance();
    private static final QuestManager questManager = new QuestManager(QuestManager.ObjectivePointer.MOVE_TUTORIAL);
    private final InputManager inputManager;
    private final EntityManager entityManager;
    private final Renderer renderer;
    private final UIManager uiManager;

    // Method call counter.
    private static int gameStateMethodCall;
    private static int gamePauseMethodCall;

    @SuppressWarnings("UnnecessarySemicolon")
    public enum GameState {
        /**
         * The state when player is playing the game.
         */
        PLAYING_STATE,
        /**
         * The state when developer is debugging the game.
         */
        EDITOR_STATE,
        /**
         * The state when the game is paused by player.
         */
        PAUSE_STATE, DEBUG_STATE;
    }

    private Game() {
        Game.font_m6x11plus = createFont("/font/m6x11plus.ttf");
        Game.gameSettings = new GameSettings();
        Game.gameState = GameState.PLAYING_STATE;
        Game.randomGenerator = new Random(GameLoop.generateRandomSeed());
        Game.gameStateMethodCall = 0;
        Game.gamePauseMethodCall = 0;

        final KeyboardListener keyboardListener = KeyboardListener.getInstance();
        final MouseInputListener mouseInputListener = MouseInputListener.getInstance();
        this.inputManager = InputManager.getInstance(keyboardListener, mouseInputListener);
        this.entityManager = EntityManager.getInstance();
        this.renderer = Renderer.getInstance();
        this.uiManager = UIManager.getInstance();

//        super.setPreferredSize(new Dimension(GAME_WINDOW_SIZE.getWidth(), GAME_WINDOW_SIZE.getHeight()));
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        super.setDoubleBuffered(true);
        super.setFocusable(true);
        super.setFocusTraversalKeysEnabled(false);

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
        inputManager.update();
        uiManager.update();
        if (Game.gameState == GameState.PLAYING_STATE || Game.gameState == GameState.DEBUG_STATE) {
            if (gameSettings.isGamePause()) {
                entityManager.update(deltaTime);
                questManager.update();
            }
        } else if (Game.gameState == GameState.EDITOR_STATE) {
            System.out.println("Editor state");
        } else if (Game.gameState == GameState.PAUSE_STATE) {
            System.out.println("Pause state");
        } else {
            System.out.println("[WARNING]:  Invalid state [" + Game.gameState + "]");
        }
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

        renderer.render(g2d);
        uiManager.render(g2d);
        g2d.dispose();
    }

    @SuppressWarnings("SameParameterValue")
    private Font createFont(final String filePath) {
        InputStream iS = Game.class.getResourceAsStream(filePath);
        if (iS == null) {
            System.out.println("[WARNING]:  Unable to find file " + filePath);
            return null;
        }
        try {
            return Font.createFont(Font.TRUETYPE_FONT, iS);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("[ERROR]:  Could not find font through file path [" + filePath + "]");
        }
    }

    /**
     * Toggle the game from pause to resume or from resume to pause.
     * Note: This is the wrapper method of {@link GameSettings#toggleUpdateGame()}
     */
    public static void toggleGamePauseResume() {
        gameSettings.toggleUpdateGame();
        gameState = (gamePauseMethodCall % 2 == 0) ? GameState.PAUSE_STATE : GameState.PLAYING_STATE;
        gamePauseMethodCall++;
    }

    public static void switchGameState() {
        switch (gameStateMethodCall % GameState.values().length) {
            case 0 -> gameState = GameState.PLAYING_STATE;
            case 1 -> gameState = GameState.EDITOR_STATE;
            default -> System.out.println("[WARNING]:  Invalid state [" + gameState + "]");
        }
        gameStateMethodCall++;
    }

    public static void toggleDebugMode() {
        gameSettings.toggleDebugMode();
        gameState = gameSettings.isDebugMode() ? GameState.DEBUG_STATE : GameState.PLAYING_STATE;
    }

    public static boolean isDebugMode() {
        return gameSettings.isDebugMode();
    }

    // =============================================== [GETTERS & SETTERS] ===============================================

    public static Font getGameFont() {
        return Game.font_m6x11plus;
    }

    public static Random getRandomGenerator() {
        return randomGenerator;
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static Objective getCurrentObjective() {
        return questManager.getCurrentObjective();
    }
}
