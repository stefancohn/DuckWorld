package entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import levels.LevelManager;
import util.Constants;
import util.LoadSave;

public class EnemyManager {
    ArrayList<Goose> enemies = new ArrayList<Goose>(); //array list to hold our enemy geese

    int[][] spawnPoints; //spawn points for the spawnGooseDefault method

    int width = 36; //widht and height for geese
    int height = 36;

    Random spawnGooseChance = new Random();

    LevelManager levelManager;
    int[][] levelData;

    public EnemyManager(LevelManager levelManager) {
        this.levelManager = levelManager;
        levelData = levelManager.getCurrentLevel().getLevelData();
        spawnGooseDefault();
    }

    public void spawnGooseDefault() { //this method spawns the goose for the default levle that shows when the game starts
        spawnPoints = LoadSave.getLevelDataBlue("res/levelOne.png");
        for (int i = 0; i < spawnPoints.length; i++) { 
            for (int j = 0; j < spawnPoints[i].length; j++) { //gets level data regarding blue squares, ones with value 1 spawn a goose
                int value = spawnPoints[i][j];
                if (value == 1) {
                    enemies.add(new Goose(j * Constants.TILES_SIZE, i * Constants.TILES_SIZE, width, height, levelData));
                }
            }
        }
    }

    public void spawnGooseRandom() {
        int[][] levelData = levelManager.getCurrentLevel().getLevelData();
        for (int i = 2; i <levelData.length; i++) {
            int col49 = levelData[i][49];
            int col48 = levelData[i][48];
            int col47 = levelData[i][47];
            if ((col49 == 0 || col49 == 3) && (col48 == 0 || col48 == 3) && (col47 == 0 || col47 == 3) &&  //makes sure the blocks under are ground
            levelData[i-1][47] == 4 && levelData[i-2][47] == 4) { //ensures the two blocks above the spawn point are blank
                int randomVal = spawnGooseChance.nextInt(101);
                if (randomVal <= 5) { //5% chance an enemy spawns if spawn conditions are met 
                    enemies.add(new Goose(47 * Constants.TILES_SIZE, i * Constants.TILES_SIZE - 32 - height, width, height, levelData));
                }
            }
        }
    }

    public void callXOffsetGoose() {
        for (Goose goose : enemies) {
            goose.xOffsetForConstantMove(Constants.MOVE_SCREEN_RIGHT_LENGTH * Constants.TILES_SIZE);
        }
    }

    public void update() {
        for (Goose goose : enemies) {
            goose.update();
        }
    }
    public void draw(Graphics g) {
        for (Goose goose : enemies) {
            goose.draw(g);
        }
    }
}
