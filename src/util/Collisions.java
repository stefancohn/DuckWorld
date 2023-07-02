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
        int value = levelData[yIndex + 3][xIndex]; //count for height of ducky by going under his feet
        if (value == 5) {
            return true; //if touching lava, true
        }
        return false; 
    }

    public static Boolean isOnFloor(int x, int y, int width, int height, int[][] levelData) {
        if (!canMoveHere(x, y + Constants.GRAVITY, width, height, levelData)) { //just adds gravity to duck's feet
            return true;
        }
        return false;
    }

    public static int getXPosNextToWallLeft(Rectangle hitbox) {
        int currentTile = (hitbox.x/Constants.TILES_SIZE);
        return currentTile * Constants.TILES_SIZE;
    }

    public static int getXposNextToWallRightMoving(Rectangle hitbox) {
        int currentTile = ((hitbox.x + hitbox.width)/Constants.TILES_SIZE); //get furthest block ducky's touching
        currentTile = ((currentTile + 1) * 16); //transform it to pixel size
        int xOffset = currentTile - Ducky.duckDimensionsIdle; //subtract ducky's width to get one pixel in block ducky's touching 
        return xOffset - 1; //subtract by one so he's not in it
    }
    public static int getXposNextToWallRightIdleInAir(Rectangle hitbox) {
        int currentTile = (((hitbox.x - 16) + hitbox.width)/Constants.TILES_SIZE);
        System.out.println("CURERNT TIEL 1 : " + currentTile);
        currentTile = ((currentTile + 1) * 16);
        System.out.println("Current TILE 2: " + currentTile);
        int xOffset = currentTile - Ducky.duckDimensionsIdle;
        System.out.println("xOFFSET: " + xOffset);
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

    //check if two entities colldie
    public static Boolean entityCollide(Rectangle entity1, Rectangle entity2) {
        //checks if their hitboxes overlap 
        if (entity2.x > entity1.x && entity2.x < entity1.x + entity1.width
         && entity2.y > entity1.y && entity2.y < entity1.y + entity1.height) {
            return true;
        } if (entity2.x + entity2.width > entity1.x && entity2.x + entity2.width < entity1.x + entity1.width
        && entity2.y > entity1.y && entity2.y < entity1.y + entity1.height) {
            return true;
        }
        //else return false
        return false;
    }

    public static Boolean canMoveHere(int x, int y, int width, int height, int[][] levelData){
        Boolean isNotSolid = false;
        //checks if the corners collide with anything
        if (isSolid(x, y, levelData) == false) {
            if (isSolid(x + width, y, levelData) == false) {
                if (isSolid(x , y + height, levelData) == false) { 
                    if (isSolid(x+width, y + height, levelData) == false) {
                        isNotSolid = true;
                    }
                }
            }
        } 
        //checks anything inbetween the corners
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (isSolid(x + i, y + j, levelData)) {
                        isNotSolid = false;
                }
            }
        }
        return isNotSolid;
    }
}
