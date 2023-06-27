package statemanager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import entity.Ducky;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import util.Constants;
import java.util.Random;

public class PlayingScene extends Scene {
    Ducky duck;
    LevelManager levelManager = new LevelManager();

    //variables used for random generation of obstacles and screen move
    int timerForConstantScreenMoveMethod = 0;
    static int moveScreenRightLength = 1;
    int obstacleCounter = 0;
    Random patternChooser = new Random();
    int pattern = patternChooser.nextInt(Constants.AMOUNT_OF_PATTERNS);
    
    PauseOverlay pauseScreen = new PauseOverlay(Game.getGame().getPanel().getMouseHandler());
    public static Boolean unpaused = false;
    int unpauseCounter = 0;
    int displayedCountdown = 3;

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

    public void unpauseTimer() {
        unpauseCounter++;
        if (unpauseCounter % Constants.UPS == 0) {
            displayedCountdown--;
        }
        if (unpauseCounter > 360) {
            unpauseCounter = 0;
            displayedCountdown = 3;
            PlayingScene.unpaused = false;
        }
    }

    @Override
    public void update() {
        if (PlayingScene.unpaused) {
            unpauseTimer();
        }
        else if (!duck.kh.getPause()) {
            duck.update();
            constantScreenMove();
        } else if (duck.kh.getPause()) {
            pauseScreen.update();
        }
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        duck.draw(g);
        if (duck.kh.getPause()) {
            pauseScreen.draw(g);
        }
        if (PlayingScene.unpaused) {
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            g.setColor(Color.WHITE);
            if (unpauseCounter < 360) {
                g.drawString("" + displayedCountdown, 50, 50);
            }
        }
    }
    
}
