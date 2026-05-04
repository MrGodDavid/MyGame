package david.game.entity.component;

import com.mrgoddavid.vector.Vector2d;
import david.game.core.Game;
import david.game.input.InputManager;

import java.awt.*;

/**
 * Define the shoot path of player. You can think of the aiming path of player.
 *
 * @author Mr. GodDavid
 * @since 4/29/2026
 */
public final class ShootPath {

    private final double LINE_LENGTH = 2 * Game.UNIT_LENGTH;

    private final Vector2d startPosition;
    private final Vector2d endPosition;

    public ShootPath() {
        startPosition = new Vector2d();
        endPosition = new Vector2d();
    }

    public void update(Vector2d position) {
        startPosition.setValues(position.getX(), position.getY());
        double dx = InputManager.getMousePosition().getX() - startPosition.getX();
        double dy = InputManager.getMousePosition().getY() - startPosition.getY();
        double distanceToCursor = Math.sqrt(dx * dx + dy * dy);

        double Dx = LINE_LENGTH / distanceToCursor * dx;
        double Dy = LINE_LENGTH / distanceToCursor * dy;

        endPosition.setValues(startPosition.getX() + Dx, startPosition.getY() + Dy);
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(5F));
        g2d.drawLine(
                (int) startPosition.getX(),
                (int) startPosition.getY(),
                (int) endPosition.getX(),
                (int) endPosition.getY()
        );
    }
}
