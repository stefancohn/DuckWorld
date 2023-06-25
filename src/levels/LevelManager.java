package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Constants;
import util.LoadSave;

public class LevelManager {
    private BufferedImage[] levelSprite = new BufferedImage[6]; 
    private BufferedImage[] obstacleSprites = new BufferedImage[2];

    Level mainLevel;
    Level[] obstacleSequences = new Level[2];

    public LevelManager(){
        importLevelSprite();
        //initializes new level by using LoadSave getRed method to grab level
        //data of start level to feed into level constructor
        mainLevel = new Level(LoadSave.getLevelDataRed(LoadSave.START_LEVEL));
        importObstacleSequences();
    }

    //grabs each block of map sprite into an array so map can be built
    public void importLevelSprite() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        for (int i = 0; i < levelSprite.length; i++) {
            levelSprite[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    //creates levelDatas for obstacles sprites
    public void importObstacleSequences() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.OBSTACLE_SEQUENCES);
        for (int j = 0; j < (img.getWidth()/Constants.TILES_IN_WIDTH); j++) {
            obstacleSprites[j] = img.getSubimage(j * 50, 0, 50, 30);
        }
        for (int i = 0; i < obstacleSprites.length; i ++) {
            obstacleSequences[i] = new Level(LoadSave.getLevelDataRedImg(obstacleSprites[i]));
        }
    }

    //method to transplant randomly selected sequence from obstacleSequences to mainLevel
    //iterates through mainLevel data and replaces it with obstacleSequences data
    public void transformMainLevel(int xOffset, int obstacleCounter) {
        int width = mainLevel.getLevelData()[0].length - 1;
        for (int i = 0; i < mainLevel.getLevelData().length; i++) {
            for (int j = width, k =0; j > (mainLevel.getLevelData()[i].length - 1) - xOffset; j--, k++){
                mainLevel.getLevelData()[i][j] = obstacleSequences[0].getLevelData()[i][k + obstacleCounter];
            }
        }
    }

    public Level getCurrentLevel() {
        return mainLevel;
    }

    public void update() {
    }
    public void draw(Graphics g) {
       for (int i = 0; i < Constants.TILES_IN_HEIGHT; i++) 
            for (int j = 0; j < Constants.TILES_IN_WIDTH; j++) {
                int index = mainLevel.getSpriteIndex(i, j);
                //these are drawn to size because the level builder sprites are 16*16, no size def needed
                g.drawImage(levelSprite[index], j * Constants.TILES_SIZE_DEF, i * Constants.TILES_SIZE_DEF, 20, 20, null);
        }
    }
}
