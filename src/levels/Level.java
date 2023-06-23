package levels;

import java.util.Arrays;

public class Level {
    private int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex (int x, int y) {
        return levelData[x][y];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public void shiftLevelRight(int xOffset) {
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length - xOffset; j++) {
                levelData[i][j] = levelData[i][j + xOffset];
            }
        }
        /*for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length - xOffset; j++) {
                System.out.println(Arrays.toString(levelData[i]));
            }
        }
        */
    }

    
}
