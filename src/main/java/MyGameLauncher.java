import core.GameWrapper;

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
public final class MyGameLauncher {

    public static void main(String[] args) {
        GameWrapper gameWrapper = new GameWrapper();
        gameWrapper.launch();
    }
}
