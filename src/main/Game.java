package main;

import java.awt.Graphics;
import entity.Ducky;
import levels.LevelManager;
import util.Constants;

public class Game implements Runnable {
    //main creates game class, creates thread, panel, frame, duck, and levelManager
    //panel holds game, and mouse/keyhandlers 
    public static Game game = null;
    Thread GameThread = new Thread(this);
    GamePanel panel = new GamePanel(this);
    GameFrame frame = new GameFrame(panel);
    Ducky duck = new Ducky(panel.kh, 16, 16, 40, 40);
    LevelManager levelManager = new LevelManager(this);

    public Game() {
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        //to give ducky necessary level data, we got to levelManager which gets
        //the level we are working wtih thich returns the levelData
        duck.getLevelData(levelManager.getCurrentLevel().getLevelData());
    }
    //singleton game panel
    public static Game getGame() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return game;
    }

    public void startGameThread() {
        GameThread.start();
    }
    
    public Ducky getDucky() {
        return duck;
    }

    public GamePanel getPanel() {
        return panel;
    }

    public GameFrame getFrame() {
        return frame;
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
        levelManager.draw(g);
        duck.draw(g);
    }      
}