package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Collisions;
import util.Constants;
import util.LoadSave;

public class Goose extends Entity {
    BufferedImage[][] gooseImages = new BufferedImage[2][1];

    int[][] levelData;

    int gooseSpeed = -Constants.ENEMY_SPEED; //starts negative b/c want to start moving left

    //animation variables 
    String direction = "";
    int spriteRow = 0;

    //variables for movement
    private boolean movingLeft = true;  
    private int patrolDistance = 200;    
    private int traveledDistance = 0;

    Boolean isDead = false;

    public Goose(int x, int y, int width, int height, int[][] levelData) {
        super(x, y , width, height);
        this.levelData = levelData;
        initializeHitbox(x, y, width, height);
        initializeGoose();
    }

    private void initializeGoose() { //put images of goose into bufferedimage array
        BufferedImage img = LoadSave.getSpriteAtlas("res/Goose.png");
        for (int i = 0; i < gooseImages.length; i++) {
            for (int j = 0; j < gooseImages[i].length; j++) {
                gooseImages[i][j] = img.getSubimage(j * 16, i * 16, 16, 16);
            }
        }
    }

    public void initializeLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    public void setAni() { //sets correct row for sprite sheet
        switch (direction) {
            case "right":
                spriteRow = 1;
                break;
            case "left":
                spriteRow = 0;
                break;
        }
    }

    public void movement() {
        //patrol logic
        if (traveledDistance >= patrolDistance) {  //once reaches desired patrol distance, restart variables
            movingLeft = !movingLeft; 
            traveledDistance = 0;        
        }
    
        if (movingLeft) { // move left/right depending on movingLeft boolean
            hitbox.x += gooseSpeed;
            direction = "left";
        } else {
            hitbox.x -= gooseSpeed;
            direction = "right";
        }
    
        if (Collisions.canMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData) 
        && (Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData))) { //if can move, ad to traveled distacne
            traveledDistance += Math.abs(gooseSpeed);
        } else {
            movingLeft = !movingLeft; //if there is a collision, flip the direction, reset travelled distance
            traveledDistance = 0;
        }
        if (!Collisions.isOnFloor(hitbox.x + 1, hitbox.y, hitbox.width, hitbox.height, levelData) && !movingLeft) {
            movingLeft = !movingLeft; //if there is no ground while moving RIGHT, flip direction, reset traveled distance
            traveledDistance = 0;
        }
        if (!Collisions.isOnFloor(hitbox.x - 1, hitbox.y, hitbox.width, hitbox.height, levelData) && movingLeft) {
            movingLeft = !movingLeft; //if there is no ground while moving LEFT, flip direction, reset traveled distance
            traveledDistance = 0;
        }

        //gravity
        if (!Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += Constants.GRAVITY;
        } else {
            hitbox.y = Collisions.getYposFloorBelow(hitbox);
        }
    }

    public void xOffsetForConstantMove(int xOffset) {
        hitbox.x -= xOffset;
    }

    public void setDead(Boolean flag) {
        this.isDead = flag;
    }

    public Boolean gooseDead() {
        if (hitbox.x <= 0) {
            isDead = true;
        }
        return isDead;
    }

    public void update() {
        movement();
        setAni();
    }
    public void draw(Graphics g) {
        g.drawImage(gooseImages[spriteRow][0], hitbox.x, hitbox.y, width, height, null);
        //drawHitbox(g);
    }
}