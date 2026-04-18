package core;

import entity.EntityManager;
import entity.GameCharacter;
import entity.item.AbstractItem;
import entity.projectile.Projectile;

import java.awt.*;

/**
 * @author Mr. GodDavod
 * @since 4/18/2026
 */
public final class Renderer {

    private static class RenderingSettings {

        private static RenderingSettings instance;
        private boolean renderCollisionBoxEnabled;

        private RenderingSettings() {
            this.renderCollisionBoxEnabled = false;
        }

        public static RenderingSettings getInstance() {
            if (instance == null) {
                instance = new RenderingSettings();
            }
            return instance;
        }

        public void setRenderCollisionBoxEnabled(boolean renderCollisionBoxEnabled) {
            this.renderCollisionBoxEnabled = renderCollisionBoxEnabled;
        }

        public boolean isRenderCollisionBoxEnabled() {
            return renderCollisionBoxEnabled;
        }
    }

    private static Renderer instance;
    private static RenderingSettings settings;

    private Renderer() {
        settings = RenderingSettings.getInstance();
    }

    public static Renderer getInstance() {
        if (instance == null) {
            instance = new Renderer();
        }
        return instance;
    }

    /**
     * Render all {@code MovingEntity} of this game every frame.
     *
     * @param g2d that can be considered as the graphics rendering pipeline that built inside {@link Graphics2D} class.
     */
    public void render(Graphics2D g2d) {
        renderAllGameCharacters(g2d);
        renderAllProjectiles(g2d);
        renderAllItemsOnScreen(g2d);

        if (settings.isRenderCollisionBoxEnabled()) {
            renderAllGameObjectsCollisionBound(g2d);
        }
    }

    private void renderAllItemsOnScreen(Graphics2D g2d) {
        for (AbstractItem item : EntityManager.getItems()) {
            if (item.isDraw() && item.inCamera()) {
                g2d.drawImage(
                        item.getSprite(),
                        (int) item.getPosition().getX(),
                        (int) item.getPosition().getY(),
                        null
                );
            }
        }
    }

    private void renderAllProjectiles(Graphics2D g2d) {
        for (Projectile projectile : EntityManager.getProjectiles()) {
            if (projectile.inCamera() && projectile.isAlive()) {
                g2d.drawImage(
                        projectile.getSprite(),
                        (int) projectile.getPosition().getX(),
                        (int) projectile.getPosition().getY(),
                        null
                );
            }
        }
    }

    private void renderAllGameCharacters(Graphics2D g2d) {
        for (GameCharacter character : EntityManager.getGameCharacters()) {
            if (character.inCamera()) {
                g2d.drawImage(
                        character.getSprite(),
                        (int) character.getPosition().getX(),
                        (int) character.getPosition().getY(),
                        null
                );
                character.render(g2d);
            }
        }
    }

    private void renderAllGameObjectsCollisionBound(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        for (AbstractItem item : EntityManager.getItems()) {
            if (item.hasCollisionBox() && item.inCamera()) {
                g2d.drawRect(
                        (int) item.getCollisionBox().getBoundingBox().getX(),
                        (int) item.getCollisionBox().getBoundingBox().getY(),
                        (int) item.getCollisionBox().getBoundingBox().getWidth(),
                        (int) item.getCollisionBox().getBoundingBox().getHeight()
                );
            }
        }
        g2d.setColor(Color.GREEN);
        for (Projectile projectile : EntityManager.getProjectiles()) {
            if (projectile.hasCollisionBox() && projectile.inCamera()) {
                g2d.drawRect(
                        (int) projectile.getCollisionBox().getBoundingBox().getX(),
                        (int) projectile.getCollisionBox().getBoundingBox().getY(),
                        (int) projectile.getCollisionBox().getBoundingBox().getWidth(),
                        (int) projectile.getCollisionBox().getBoundingBox().getHeight()
                );
            }
        }
        g2d.setColor(Color.BLUE);
        for (GameCharacter character : EntityManager.getGameCharacters()) {
            if (character.hasCollisionBox() && character.inCamera()) {
                g2d.drawRect(
                        (int) character.getCollisionBox().getBoundingBox().getX(),
                        (int) character.getCollisionBox().getBoundingBox().getY(),
                        (int) character.getCollisionBox().getBoundingBox().getWidth(),
                        (int) character.getCollisionBox().getBoundingBox().getHeight()
                );
            }
        }
    }

    public static void setRenderCollisionBoxEnabled(boolean enabled) {
        settings.setRenderCollisionBoxEnabled(enabled);
    }
}
