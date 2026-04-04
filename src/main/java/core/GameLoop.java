package core;

import input.InputManager;

/**
 * Game loop of this game.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class GameLoop implements Runnable {

    private static GameLoop instance;
    private final Game game;

    private boolean running;
    private long nextStatTime;
    private final double updateRate = 1.0d / 60.0d;
    private int fps, ups;

    private GameLoop(Game game) {
        this.game = game;
        running = false;
    }

    public void start() {
        final Thread gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    public static GameLoop getInstance(Game game) {
        if (instance == null) {
            instance = new GameLoop(game);
        }
        return instance;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        running = true;
        final double TARGET_FPS = 60.0;
        final double FRAME_TIME = 1_000_000_000.0 / TARGET_FPS;

        long lastTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            long updateTime = currentTime - lastTime;

            if (updateTime >= FRAME_TIME) {
                double deltaTime = updateTime / 1_000_000_000.0;
                update(deltaTime);
                render();
                InputManager.endFrame();

                lastTime = currentTime;
                printStat();
            } else {
                try {
                    Thread.sleep(1); // ChatGPT suggests me to do this... Wth is this???
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update(double deltaTime) {
        ups++;
        game.update(deltaTime);
    }

    private void render() {
        fps++;
        game.render();
    }

    private void printStat() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.print("[MyGame V1 StatPrinter]: \t");
            System.out.printf("[FPS: %d, UPS: %d] \t||\t", fps, ups);
            fps = ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;

            System.gc();
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            System.out.printf("[Used: %d MB | Free: %d MB | Total: %d MB]\n",
                    usedMemory / (1024 * 1024),
                    freeMemory / (1024 * 1024),
                    totalMemory / (1024 * 1024));
        }
    }
}
