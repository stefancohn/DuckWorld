package main;

import java.awt.Graphics;
import entity.Ducky;
import util.*;
import statemanager.*;

public class Game implements Runnable {
    public static Game game = null;
    Thread GameThread = new Thread(this);
    GamePanel panel = new GamePanel(this);
    GameFrame frame = new GameFrame(panel);

    Ducky duck = new Ducky(panel.kh, 100, 200, 40, 40);

    Scene currentScene;
    int sceneNum = Constants.SCENE_MENU;


    public Game() {
        changeState(sceneNum);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    //singleton game panel
    public static Game getGame() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return game;
    }
    public GamePanel getPanel() {
        return this.panel;
    }

    //start game
    public void startGameThread() {
        GameThread.start();
    }

    //get rid of lost focus bug
    public void windowFocusLost() {
        duck.resetDir();
    }

    //change state
    public void changeState(int sceneNum) {
        switch (sceneNum) {
            case Constants.SCENE_MENU: 
                currentScene = new MenuScene(panel.mh);
                break;
            case Constants.SCENE_PLAYING:
                currentScene = new PlayingScene(duck);
                break;
            default:
                currentScene = null;
                break;
        }
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

    //all rendering and updating stems from here 
    public void update() {
        currentScene.update();
    }
    public void draw(Graphics g) {
        currentScene.draw(g);
    }      
}