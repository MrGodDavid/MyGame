package core;

/**
 * Game loop of this game.
 */
public final class GameLoop implements Runnable {

    private Thread gameLoopThread;

    private boolean running;
    private long nextStatTime;
    private final double updateRate = 1.0d / 60.0d;
    private int fps, ups;

    public GameLoop() {
        running = false;
    }

    public void start() {
        gameLoopThread = new Thread(this);
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
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000.0d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            if (accumulator >= updateRate) {
                while (accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
                }
                render();
            }
            printStat();
        }
    }

    private void update() {
        ups++;
    }

    private void render() {
        fps++;
    }

    private void printStat() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.print("[ChoiceCraft_V1_0_6_Alpha.game.ChoiceCraft StatPrinter]: \t");
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
