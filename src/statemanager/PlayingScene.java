package statemanager;

import java.awt.Graphics;
import entity.Ducky;
import levels.LevelManager;
import util.Constants;
import java.util.Random;

public class PlayingScene extends Scene {
    Ducky duck;
    LevelManager levelManager = new LevelManager();

    int timerForConstantScreenMoveMethod = 0;
    static int moveScreenRightLength = 1;
    int obstacleCounter = 0;
    Random patternChooser = new Random();
    int pattern = patternChooser.nextInt(Constants.AMOUNT_OF_PATTERNS);

    public PlayingScene(Ducky duck) {
        this.duck = duck;
        duck.initiateLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    //implements the shiftLevelRight thingy to shift the level every 40 updates
    public void constantScreenMove() { 
        timerForConstantScreenMoveMethod++;
        if (timerForConstantScreenMoveMethod % 40 == 0) {
            duck.xOffsetForConstantMove(moveScreenRightLength * Constants.TILES_SIZE);
            levelManager.getCurrentLevel().shiftLevelRight(moveScreenRightLength);
            //moves ducky with the xOffset(moveScreenRightLength) so he is updated correctly
            if (obstacleCounter < 50) {
                levelManager.transformMainLevel(moveScreenRightLength, obstacleCounter, pattern);
                obstacleCounter+= moveScreenRightLength;
            } else {
                obstacleCounter = 0;
                pattern = patternChooser.nextInt(Constants.AMOUNT_OF_PATTERNS);
            }
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
