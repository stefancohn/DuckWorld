package statemanager;

import java.awt.Graphics;
import entity.Ducky;
import levels.LevelManager;
import util.Constants;

public class PlayingScene extends Scene {
    Ducky duck;
    LevelManager levelManager = new LevelManager();

    int timerForConstantScreenMoveMethod = 0;
    int moveScreenRightLength = 1;

    public PlayingScene(Ducky duck) {
        this.duck = duck;
        duck.initiateLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    public void constantScreenMove() { 
        timerForConstantScreenMoveMethod++;
        if (timerForConstantScreenMoveMethod % 100 == 0) {
            levelManager.getCurrentLevel().shiftLevelRight(moveScreenRightLength);
            duck.xOffsetForConstantMove(moveScreenRightLength * Constants.TILES_SIZE);
        }
    }

    @Override
    public void update() {
        duck.update();
        constantScreenMove();
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        duck.draw(g);
    }
    
}
