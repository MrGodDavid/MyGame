package core;

import input.InputManager;

/**
 * Game loop of this game.
 *
 * @author Mr. GodDavid
 * @since 3/30/2026
 */
public final class GameLoop implements Runnable {

    private final Game game;

    private boolean running;
    private long nextStatTime;
    private final double updateRate = 1.0d / 60.0d;
    private int fps, ups;

    public GameLoop(Game game) {
        this.game = game;
        running = false;
    }

    public void start() {
        final Thread gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        running = true;
        double accumulator = 0.0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (running) {
            currentTime = System.currentTimeMillis();
            double deltaTime = (currentTime - lastUpdate) / 1000.0d;
            accumulator += deltaTime;
            lastUpdate = currentTime;

            if (accumulator >= updateRate) {
                while (accumulator >= updateRate) {
                    update(updateRate);
                    accumulator -= updateRate;
                    InputManager.endFrame();
                }
                render();
            }
            printStat();
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

            Runtime runtime = Runtime.getRuntime();
            System.gc();
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
