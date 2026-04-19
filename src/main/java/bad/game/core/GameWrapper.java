package bad.game.core;

import bad.game.annotation.SingletonClass;

/**
 * Wrapper class of this game. Create a window and run a Thread of this game.
 * <p>This class only contains {@code Window} and {@code GameLoop}.</p>
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
@SingletonClass
public final class GameWrapper {

    private static GameWrapper instance;

    private final GameWindow gameWindow;
    private final GameLoop gameLoop;

    private GameWrapper() {
        Game game = Game.getInstance();
        gameWindow = GameWindow.getInstance(game);
        gameLoop = GameLoop.getInstance(game);
    }

    public static GameWrapper getInstance() {
        if (instance == null) {
            instance = new GameWrapper();
        }
        return instance;
    }

    /**
     * Launch the game.
     */
    public void launch() {
        gameWindow.show();
        gameLoop.start();
    }
}
