package util;

public class Constants {
    //values to be used across whole game
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 750;
    public static final int FPS = 120;
    public static final int UPS = 120;

    public static final int TILES_SIZE_DEF = 16;
    public static final float SCALE = 1.0f;
    public static final int TILES_IN_WIDTH = 50;
    public static final int TILES_IN_HEIGHT = 30;
    public static final int TILES_SIZE = (int)(TILES_SIZE_DEF * SCALE);
    //800 x 480
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public static final int GRAVITY = 3;
    public static final int DUCKY_SPEED = 2; 
    public static final int DUCKY_IDLE = 0;
    public static final int DUCKY_RIGHT = 1;
    public static final int DUCKY_LEFT = 2;
    public static final int DUCKY_ATTACK_RIGHT = 3; 
    public static final int DUCKY_ATTACK_LEFT = 4;
    public static final int DUCKY_DEATH = 5;

    public static final int SCENE_MENU = 0;
    public static final int SCENE_PLAYING = 1;
    public static final int SCENE_DEATH = 2;

    public static final int AMOUNT_OF_PATTERNS = 6;
    public static final int MOVE_SCREEN_RIGHT_LENGTH = 1;

    public static final int ENEMY_SPEED = 1;
}
