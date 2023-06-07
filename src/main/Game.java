package main;

public class Game implements Runnable {
    Thread GameThread = new Thread(this);
    GamePanel panel;

    public Game() {
        panel = new GamePanel();
        GameFrame frame = new GameFrame();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static class Constants {
        //values to be used across whole game
        public static final int SCREEN_WIDTH = 1000;
        public static final int SCREEN_HEIGHT = 750;
        public static final int FPS = 120;
        public static final int UPS = 60;
        public static final int SCALE = 3;
        public static final int TILE_SIZE = 16 * SCALE;
    }

    public void startGameThread() {
        GameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/Constants.FPS;
        double updateInterval = 1000000000/Constants.FPS;
        double deltaT = 0; 
        double deltaU = 0;
        long lastTime = System.nanoTime();
        long currentTime; 

        while (GameThread != null) {
            currentTime = System.nanoTime();
            deltaT += (currentTime - lastTime) / drawInterval;
            deltaU += (currentTime - lastTime) / updateInterval;
            lastTime = currentTime;

            if (deltaU >= 1) {
                update();
                deltaU--;
            }
            if (deltaT >= 1) {
                panel.repaint();
                deltaT--;
            }
        }
    }

    public void update() {
    }
         
}