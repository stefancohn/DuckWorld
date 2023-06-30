package util;

import java.awt.Rectangle;

import entity.Ducky;

public class Collisions {
    private static Boolean isSolid(int x, int y, int[][] levelData) {
        //checks if it hits side of screen
        if (x<0 || x>= Constants.GAME_WIDTH) {
            return true;
        }
        else if(y<=0 || y>= Constants.GAME_HEIGHT) {
            return true;
        } 
        //check blocks by ensuring only runs into background block
        int xIndex = x / Constants.TILES_SIZE;
		int yIndex = y / Constants.TILES_SIZE;
        int value = levelData[yIndex][xIndex];
        if (value != 4 ) {
            return true;
        }
        return false;
    }

    public static Boolean touchedLava(int x, int y, int[][] levelData) {
        int xIndex = x / Constants.TILES_SIZE;
		int yIndex = y / Constants.TILES_SIZE;
        if (xIndex < 0 ) {
            return false;
        }
        int value = levelData[yIndex + 3][xIndex];
        if (value == 5) {
            return true;
        }
        return false; 
    }

    public static Boolean isOnFloor(int x, int y, int width, int height, int[][] levelData) {
        if (!canMoveHere(x, y + Constants.GRAVITY, width, height, levelData)) {
            return true;
        }
        return false;
    }

    public static int getXPosNextToWallLeft(Rectangle hitbox) {
        int currentTile = (hitbox.x/Constants.TILES_SIZE);
        return currentTile * Constants.TILES_SIZE;
    }

    public static int getXposNextToWallRightMoving(Rectangle hitbox) {
        int currentTile = ((hitbox.x + hitbox.width)/Constants.TILES_SIZE);
        currentTile = ((currentTile + 1) * 16);
        int xOffset = currentTile - Ducky.duckDimensionsSide;
        return xOffset - 1;
    }
    public static int getXposNextToWallRightIdle(Rectangle hitbox) {
        int currentTile = ((hitbox.x + hitbox.width)/Constants.TILES_SIZE);
        currentTile = ((currentTile + 1) * 16);
        int xOffset = currentTile - Ducky.duckDimensionsIdle;
        return xOffset - 1;
    }
    public static int getYPosCeilingAbove(Rectangle hitbox) {
        int currentTile = (hitbox.y/Constants.TILES_SIZE);
        return currentTile * Constants.TILES_SIZE;
    }
    public static int getYposFloorBelow(Rectangle hitbox) {
        int currentTile = ((hitbox.y + hitbox.height)/Constants.TILES_SIZE);
        currentTile = ((currentTile + 1) * 16);
        int yOffset = currentTile - hitbox.height;
        return yOffset - 1;
    }

    public static Boolean canMoveHere(int x, int y, int width, int height, int[][] levelData){
        if (isSolid(x, y, levelData) == false) {
            if (isSolid(x + width, y, levelData) == false) {
                if (isSolid(x , y + height, levelData) == false) {
                    if (isSolid(x + (width/2), y + (height/2), levelData) == false) {
                        if (isSolid(x+width, y + height, levelData) == false) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
