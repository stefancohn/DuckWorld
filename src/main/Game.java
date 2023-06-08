package main;

import java.awt.Graphics;

import entity.Ducky;
import util.Constants;

public class Game implements Runnable {
    Thread GameThread = new Thread(this);
    GamePanel panel = new GamePanel(this);
    GameFrame frame = new GameFrame(panel);;
    Ducky duck = new Ducky(panel.kh, 0, 0);

    public Game() {
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void startGameThread() {
        GameThread.start();
    }
    
    public Ducky getDucky() {
        return duck;
    }

    public void windowFocusLost() {
        duck.resetDir();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/Constants.FPS;
        double updateInterval = 1000000000/Constants.UPS;
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
        duck.update();
    }
    public void draw(Graphics g) {
        duck.draw(g);
    }      
}