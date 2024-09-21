package p02.game;

public class GameThread implements Runnable {
    private static GameThread instance;
    private Board board;
    private boolean running = false;
    private int interval = 1000;

    private GameThread(Board board) {
        this.board = board;
    }

    public static GameThread getInstance(Board board) {
        if (instance == null) {
            instance = new GameThread(board);
        }
        return instance;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(interval);
                board.fireEvent(new TickEvent(this));
                board.tick();
                interval -= 5;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    public void resetInterval() {
        interval = 1000;
    }
}