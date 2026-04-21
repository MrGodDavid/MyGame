package david.game;

import bad.code.format.annotation.UninstantiableClass;
import david.game.core.GameWrapper;

/**
 * Launcher class of this game.
 * <p>
 * Contain only main() method that is the entry point of every Java program.
 * <pre><code>
 * public static void main(String[] args) {
 *     // other codes are not shown
 *     game.launch();
 * }
 * </code></pre>
 * </p>
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
@UninstantiableClass
public final class MyGameLauncher {

    private MyGameLauncher() {
        throw new IllegalStateException("This is a root game launcher class and cannot be instantiated");
    }

    public static void main(String[] args) {
        GameWrapper gameWrapper = GameWrapper.getInstance();
        gameWrapper.launch();
    }
}
