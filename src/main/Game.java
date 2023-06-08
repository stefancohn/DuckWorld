package main;

import util.Constants;

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
        panel.update();
    }

    public void draw() {

    }
         
}