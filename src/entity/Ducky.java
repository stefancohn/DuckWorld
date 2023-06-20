package entity;
import handler.KeyHandler;
import util.Collisions;
import util.Constants;
import util.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

    public class Ducky extends Entity {
        BufferedImage duckSprite;

        public static int duckDimensionsIdle = 38; 
        public static int duckDimensionsSide = 22;
        public static int duckOffsetX = 10;

        BufferedImage[][] duckAni = new BufferedImage[5][4];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 15;

        KeyHandler kh = new KeyHandler();

        String direction = "";

        Boolean isAttacking = false;

        //for making sure the jump input can be properly turned off/on
        Boolean jumping = false;
        Boolean inAir = false;
        public int airSpeed = 0;
        public int jumpHeight = -80;
        public int gravity = 2;
        
        int[][] levelData;

        public Ducky(KeyHandler kh, int x, int y, int width, int height) {
            super(x, y, width, height);
            duckSprite = LoadSave.getSpriteAtlas(LoadSave.DUCKY_ATLAS);
            loadAni();
            this.kh = kh;
            initializeHitbox(x, y, width, height);
        }

        public void getLevelData(int[][] levelData) {
            this.levelData = levelData;
        }

        private void loadAni() {
            for (int i =0; i < duckAni.length; i++) {
                for (int j = 0; j < duckAni[i].length; j++) {
                    duckAni[i][j] = duckSprite.getSubimage(j * 16, i * 16, Constants.TILES_SIZE, Constants.TILES_SIZE);
                }
            }
        }

        private void setAni() {
            int startAni = spriteRow;
            switch (direction) {
                case "right":
                    spriteCol = 2;
                    spriteRow = Constants.DUCKY_RIGHT;
                    break;
                case "left": 
                    spriteCol = 2;
                    spriteRow = Constants.DUCKY_LEFT;
                    break;
                case "attackingRight":
                    spriteCol = 4;
                    spriteRow = Constants.DUCKY_ATTACK_RIGHT;
                    break;
                case "attackingLeft":
                    spriteCol = 4;
                    spriteRow = Constants.DUCKY_ATTACK_LEFT;
                    break;
                default: 
                    spriteCol = 0;
                    spriteRow = Constants.DUCKY_IDLE;
                    hitbox.width = Ducky.duckDimensionsIdle;
                    break;
            }
            if (spriteRow != startAni) {
                aniTick = 0;
                spriteLoop = 0;
            }
        }

        public void updateAni() {
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                spriteLoop++;
                if (isAttacking && spriteLoop == 4) {
                    kh.spacePressed = false;
                    isAttacking = false;
                }
                if (spriteLoop >= spriteCol) {
                    spriteLoop = 0;
                }
            }
        }

        private void duckyMovementAndHitbox() {
            //reset attack button
            if (kh.getSpacePres() == true && kh.getRightPres() != true && kh.getLeftPres() != true 
            && kh.getDownPres() != true && kh.getUpPres() != true && isAttacking != true) {
                kh.spacePressed = false;
            }
            //idle
            if (kh.getRightPres() != true && kh.getLeftPres() != true 
             && kh.getSpacePres() != true && kh.getDownPres() != true && kh.getUpPres() != true) {
                //move pos back when going to idle pos
                if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x -= 4;
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x += 4;
                }
                direction = "";
            }
            //hit box and movement fix
            if (kh.getRightPres() == true && kh.getLeftPres() == true) {
                direction = "";
                //move pos back when going to idle pos
                if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x -= 4;
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x += 4;
                }
            }
            //moving left
            if (kh.getLeftPres() == true && kh.getRightPres() != true
            && kh.getSpacePres() != true  && Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                hitbox.x -= Constants.DUCKY_SPEED;
                direction = "left";
                updateHitboxLeft(x);
            }
            //moving right
            if (kh.getRightPres() == true && kh.getLeftPres() != true
            && kh.getSpacePres() != true && Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                hitbox.x += Constants.DUCKY_SPEED;
                direction = "right";
                updateHitboxRight(x);
            }
            //jump
            if (kh.getUpPres() == true && kh.getDownPres() != true 
            && kh.getSpacePres() != true && Collisions.canMoveHere(hitbox.x, hitbox.y - Constants.DUCKY_SPEED, hitbox.width, hitbox.height, levelData)){
                direction = "";
                hitbox.y += jumpHeight;
                kh.upPressed = false;
            }
            //moving down
            if (kh.getDownPres() && !kh.getUpPres()
            && !kh.getSpacePres() && Collisions.canMoveHere(hitbox.x, hitbox.y + Constants.DUCKY_SPEED, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += Constants.DUCKY_SPEED;
                direction = "";
            }
            //attack right
            if (kh.getSpacePres() == true && kh.getRightPres() == true 
            && kh.getLeftPres() != true) {
                isAttacking = true;
                direction = "attackingRight";
                updateHitboxRight(x);
            }
            //attack left
            if (kh.getSpacePres() == true && kh.getLeftPres() == true
            && kh.getRightPres() != true) {
                isAttacking = true;
                direction = "attackingLeft";
            }
            //gravity
            if (!Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += gravity;
            }
        }

        public void update() {
            duckyMovementAndHitbox();
            setAni();
            updateAni();
            //float xIndex = x / Constants.TILES_SIZE;
		    //float yIndex = y / Constants.TILES_SIZE;
           // System.out.println("x: " + x);
            //System.out.println("y: " + y);
        }
        public void draw(Graphics g) {
            if (direction == "right" || direction == "attackingRight") {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x - 10, hitbox.y, width, height, null);
                drawHitbox(g);
            } else if (direction == "left" || direction == "attackingLeft") {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x - 6, hitbox.y, width, height, null);
                drawHitbox(g);
            } else {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x, hitbox.y, width, height, null);
                drawHitbox(g);
            }
        }

        public void resetDir() {
            kh.downPressed = false;
            kh.upPressed = false;
            kh.leftPressed = false;
            kh.rightPressed = false;
            direction = ""; 
            isAttacking = false;
        }
    }
