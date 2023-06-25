package statemanager;

import java.awt.Graphics;
import entity.Ducky;
import levels.LevelManager;
import util.Constants;

public class PlayingScene extends Scene {
    Ducky duck;
    LevelManager levelManager = new LevelManager();

    int timerForConstantScreenMoveMethod = 0;
    static int moveScreenRightLength = 1;
    int obstacleCounter = 0;

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
                levelManager.transformMainLevel(moveScreenRightLength, obstacleCounter);
                obstacleCounter+= moveScreenRightLength;
            } else {
                obstacleCounter = 0;
            }
        }
        //goals for tmrw: introduce random object
        //to introduce new sequences, when sequence is over, reset obstacleCounter
    }

    @Override
    public void update() {
        duck.update();
        constantScreenMove();
        System.out.println(obstacleCounter);
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        duck.draw(g);
    }
    
}
