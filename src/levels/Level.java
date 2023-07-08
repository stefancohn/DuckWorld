package levels;

public class Level {
    public int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex (int x, int y) {
        return levelData[x][y];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    //shifts the level right by taking an offset value, and making a value
    //in the leveldata array take the index that offset value ahead of it
    public void shiftLevelRight(int xOffset) {
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length - xOffset; j++) {
                levelData[i][j] = levelData[i][j + xOffset];
            }
        } 
    }

    
}