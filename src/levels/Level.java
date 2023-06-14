package levels;

import entity.Ducky;

public class Level {
    private int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex (int x, int y) {
        return levelData[x][y];
    }

    
}
