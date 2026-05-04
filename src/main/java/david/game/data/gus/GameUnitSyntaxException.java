package david.game.data.gus;

/**
 * Throw this exception when the user types incorrect GUS syntax in config.json file.
 *
 * @author Mr. GodDavid
 * @since 5/2/2026
 */
public final class GameUnitSyntaxException extends RuntimeException {

    public GameUnitSyntaxException(String message) {
        super(message);
    }
}
