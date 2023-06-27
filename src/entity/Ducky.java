package entity;
import handler.KeyHandler;
import util.Collisions;
import util.Constants;
import util.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

    public class Ducky extends Entity {
        BufferedImage duckSprite;

        //duck dimensions
        public static int duckDimensionsIdle = 37; 
        public static int duckDimensionsSide = 21;

        //animation variables
        BufferedImage[][] duckAni = new BufferedImage[5][4];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 15;
        String direction = "";
        Boolean isAttacking = false;

        public KeyHandler kh = new KeyHandler();

        //gravity variables
        Boolean jump = false;
        Boolean inAir = true;
        public int airSpeed = -6;
        public int jumpHeight = -100;
        int yPosBeforeJump;
        
        int[][] levelData;

        public Ducky(KeyHandler kh, int x, int y, int width, int height) {
            super(x, y, width, height);
            duckSprite = LoadSave.getSpriteAtlas(LoadSave.DUCKY_ATLAS);
            loadAni();
            this.kh = kh;
            initializeHitbox(x, y, width, height);
        }

        public void initiateLevelData(int[][] levelData) {
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
                    aniSpeed = 15;
                }
                if (spriteLoop >= spriteCol) {
                    spriteLoop = 0;
                }
            }
        }

        private void duckyMovementAndHitbox() {
            //reset attack button
            if (kh.getSpacePres() && !kh.getRightPres() && !kh.getLeftPres() 
            && !kh.getDownPres() && !kh.getUpPres() && !isAttacking) {
                kh.spacePressed = false;
            }
            //idle
            if (!kh.getRightPres() && !kh.getLeftPres()
             && !kh.getSpacePres() && !kh.getDownPres() && !kh.getUpPres()) {
                //move pos back when going to idle pos
                direction = "";
                if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXposNextToWallRightIdle(hitbox);
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }
            //hit box and movement fix
            if (kh.getRightPres() && kh.getLeftPres()) {
                direction = "";
                //move pos back when going to idle pos
                if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXposNextToWallRightIdle(hitbox);
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }
            //moving left
            if (kh.getLeftPres() && !kh.getRightPres()
            && !kh.getSpacePres()) {
                direction = "left";
                updateHitboxSide();
                if (Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)){
                    hitbox.x -= Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }//attack left
            else if (kh.getSpacePres() && kh.getLeftPres()
            && !kh.getRightPres()) {
                isAttacking = true;
                direction = "attackingLeft";
                aniSpeed = 8;
                updateHitboxSide();
                if (kh.getUpPres()) {
                    jump();
                }
                if (Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)){
                    hitbox.x -= Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }
            //moving right
            if (kh.getRightPres() && !kh.getLeftPres()
            && !kh.getSpacePres()) {
                direction = "right";
                updateHitboxSide();
                if (Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x += Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXposNextToWallRightMoving(hitbox);
                }
            } //attack right
            else if (kh.getSpacePres() && kh.getRightPres()
            && !kh.getLeftPres()) {
                isAttacking = true;
                direction = "attackingRight";
                aniSpeed = 8;
                updateHitboxSide();
                if (kh.getUpPres()) {
                    jump();
                }
                if (Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x += Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXposNextToWallRightMoving(hitbox);
                }
            }
            //jump
            if (kh.getUpPres() && !kh.getDownPres() && !inAir 
            && !kh.getSpacePres()){
                if (!jump) {
                    jump();
                }
            }
            if (!inAir) {
                yPosBeforeJump = hitbox.y;
            }
            //jump checks
            if (inAir && jump) {
                if (Collisions.canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)){
                    hitbox.y += airSpeed;
                    if (hitbox.y < yPosBeforeJump + jumpHeight) {
                        jump = false; 
                        kh.upPressed = false;
                    }
                }
                else {
                    jump = false;
                    kh.upPressed = false;
                }
            }
            //gravity
            if (!Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += Constants.GRAVITY;
                inAir = true;
            } else if (Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                inAir = false;
                jump = false;
                hitbox.y = Collisions.getYposFloorBelow(hitbox);
                yPosBeforeJump = hitbox.y;
            }
        }

        public void jump() {
            inAir = true;
            jump = true;
        }

        public void xOffsetForConstantMove(int xOffset) {
            hitbox.x -= xOffset;
        }

        public void dead() {
            if (Collisions.touchedLava(hitbox.x, hitbox.y, levelData)) {
                System.out.println("OOF");
            }
        }

        public void update() {
            duckyMovementAndHitbox();
            setAni();
            updateAni();
            dead();
        }
        public void draw(Graphics g) {
            if (direction == "right" || direction == "attackingRight") {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x - 10, hitbox.y, width, height, null);
                //drawHitbox(g);
            } else if (direction == "left" || direction == "attackingLeft") {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x - 8, hitbox.y, width, height, null);
                //drawHitbox(g);
            } else {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x, hitbox.y, width, height, null);
                //drawHitbox(g);
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
