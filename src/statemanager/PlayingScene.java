package statemanager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import entity.Ducky;
import entity.EnemyManager;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import ui.VolumeButton;
import util.Constants;
import java.util.Random;

public class PlayingScene extends Scene {
    Ducky duck;

    LevelManager levelManager = new LevelManager();
    EnemyManager enemyManager = new EnemyManager(levelManager);

    VolumeButton volumeButton = new VolumeButton(Game.game.getPanel().getMouseHandler());

    //variables used for random generation of obstacles and screen move
    int timerForConstantScreenMoveMethod = 0;
    int obstacleCounter = 0;
    Random patternChooser = new Random();
    int pattern = patternChooser.nextInt(Constants.AMOUNT_OF_PATTERNS); //picks a number that corresponds with one of the obstacle patterns
    int shiftCounter = 0;
    int xOffset = 0;
    
    //variables for pause overlay 
    PauseOverlay pauseScreen = new PauseOverlay(Game.game.getPanel().getMouseHandler());
    public static Boolean unpaused = false;
    int unpauseCounter = 0;
    int displayedCountdown = 3;

    public static double gameScore = 0; //tracks enemies killed, sequences cleared, and is responsible for difficulty

    public PlayingScene(Ducky duck) {
        this.duck = duck;
        duck.initiateLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    //implements the shiftLevelRight thingy to shift the level every x updates depending on difficulty
    public void constantScreenMove() { 
        timerForConstantScreenMoveMethod++;
        if ((int)PlayingScene.gameScore > 6) { //caps off screen move once difficulty hits >6 to fastest screen move
            constantScreenMoveMethod();
        } else if ((int)PlayingScene.gameScore > 2) {
            if (timerForConstantScreenMoveMethod % 2 == 0) { //mid diff
                constantScreenMoveMethod();
                timerForConstantScreenMoveMethod = 0;
            }
        } else {
            if (timerForConstantScreenMoveMethod % 3 == 0) { //lowest diff
                constantScreenMoveMethod();
                timerForConstantScreenMoveMethod = 0;
            }
        }
    } 

    public void constantScreenMoveMethod() {
        xOffset++;
        shiftCounter++;
        if (shiftCounter % 16 == 0) { //once a whole tile has been "shown" we shift blocks 
            levelManager.getCurrentLevel().shiftLevelRight(Constants.MOVE_SCREEN_RIGHT_LENGTH);
            duck.xOffsetForConstantMove(16);
            enemyManager.callXOffsetGoose(16);
            //moves ducky with the xOffset(moveScreenRightLength) so he is updated correctly
            if (obstacleCounter < 25) { 
                levelManager.transformMainLevel(Constants.MOVE_SCREEN_RIGHT_LENGTH, obstacleCounter, pattern);
                enemyManager.spawnGooseRandom();
                obstacleCounter+= Constants.MOVE_SCREEN_RIGHT_LENGTH;
            } else { //restart screen moving and chose new level pattern when level sequence length reached
                PlayingScene.gameScore += .2001;
                obstacleCounter = 0;
                pattern = patternChooser.nextInt(Constants.AMOUNT_OF_PATTERNS);
            }
            shiftCounter = 0;
            xOffset = 0;
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
        if (PlayingScene.unpaused) { //when unpaused, hit the timer
            unpauseTimer();
        }
        else if (!duck.kh.getPause()) { //if not paused, update ducky, enemies, and the screen
            duck.update();
            enemyManager.update();
            constantScreenMove();
        } else if (duck.kh.getPause()) { //when paused, put up the pause overlay and vol button
            pauseScreen.update();
            Game.game.getVolumeButton().update();
        }
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xOffset); //draw level
        duck.draw(g, xOffset); //draw ducky
        enemyManager.draw(g, xOffset); //draw enemies

        // draw game score
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
        g.setColor(Color.GREEN);
        g.drawString("Score: " + (int)(PlayingScene.gameScore * 5), 600, 50); 

        if (duck.kh.getPause()) { //if paused draw pause over lay
            pauseScreen.draw(g); 
            Game.game.getVolumeButton().draw(g);
        } if (PlayingScene.unpaused) { //once unpaused, give a countdown till game starts again
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            g.setColor(Color.WHITE);
            if (unpauseCounter < 360) {
                g.drawString("" + displayedCountdown, 50, 50);
            }
        }
    }
    
}