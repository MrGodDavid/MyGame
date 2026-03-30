package game;

import core.GameLoop;
import core.Window;

/**
 * Wrapper class of this game. Create a window and run a Thread of this game.
 * <p>This class only contains {@code Window} and {@code GameLoop}.</p>
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class GameWrapper {

    private Window window;
    private GameLoop gameLoop;

    public GameWrapper() {
        Game game = new Game();
        window = new Window(game);
        gameLoop = new GameLoop(game);
    }

    /**
     * Launch the game.
     */
    public void launch() {
        window.show();
        gameLoop.start();
    }
}
