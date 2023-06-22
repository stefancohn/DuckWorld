package statemanager;

import java.awt.Graphics;
import entity.Ducky;
import levels.LevelManager;

public class PlayingScene extends Scene {
    Ducky duck;
    LevelManager levelManager = new LevelManager();

    public PlayingScene(Ducky duck) {
        this.duck = duck;
        duck.getLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    @Override
    public void update() {
        duck.update();
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        duck.draw(g);
    }
    
}
