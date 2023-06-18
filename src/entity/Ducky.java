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

        BufferedImage[][] duckAni = new BufferedImage[5][4];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 15;

        KeyHandler kh = new KeyHandler();

        String direction = "";

        Boolean isAttacking = false;

        Boolean jumping = false;
        public float jumpSpeed = -2.25f;
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
                    updateHitbox(x, y);
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
            if (!Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                y += gravity;
            }
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
                    x = x - 4;
                    updateHitbox(x, y);
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x + 4;
                    updateHitbox(x, y);
                }
                direction = "";
            }
            //hit box and movement fix
            if (kh.getRightPres() == true && kh.getLeftPres() == true) {
                direction = "";
                //move pos back when going to idle pos
                if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x - 4;
                    updateHitbox(x, y);
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x + 4;
                    updateHitbox(x, y);
                }
            }
            //moving up
            if (kh.getUpPres() == true && kh.getDownPres() != true 
            && kh.getSpacePres() != true && Collisions.canMoveHere(hitbox.x, hitbox.y - Constants.DUCKY_SPEED, hitbox.width, hitbox.height, levelData)){
                    direction = "";
                    y -= Constants.DUCKY_SPEED;
                    updateHitbox(x, y);
            }
            //moving down
            if (kh.getDownPres() == true && kh.getUpPres() != true
            && kh.getSpacePres() != true && Collisions.canMoveHere(hitbox.x, hitbox.y + Constants.DUCKY_SPEED, hitbox.width, hitbox.height, levelData)) {
                direction = "";
                y+= Constants.DUCKY_SPEED;
                updateHitbox(x, y);
            }
            //moving left and up 
            if (kh.getRightPres() != true && kh.getLeftPres() == true
            && kh.getSpacePres() != true && kh.getUpPres() == true) {
                if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x + Constants.DUCKY_SPEED;
                    updateHitbox(x, y);
                }
                x -= Constants.DUCKY_SPEED;
                direction = "left";
                updateHitboxLeft(x);
            }
            //moving left and down 
            else if (kh.getRightPres() != true && kh.getLeftPres() == true
            && kh.getSpacePres() != true && kh.getDownPres() == true) {
                if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x + Constants.DUCKY_SPEED;
                    updateHitbox(x, y);
                }
                x -= Constants.DUCKY_SPEED;
                direction = "left";
                updateHitboxLeft(x);
            }
            //moving left
            else if (kh.getLeftPres() == true && kh.getRightPres() != true
            && kh.getSpacePres() != true  && Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                x -= Constants.DUCKY_SPEED;
                direction = "left";
                updateHitboxLeft(x);
            }
            //moving right and up 
            if (kh.getRightPres() == true && kh.getLeftPres() != true
            && kh.getSpacePres() != true && kh.getUpPres() == true) {
                if (!Collisions.canMoveHere(hitbox.x + 13, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x - Constants.DUCKY_SPEED;
                    updateHitbox(x, y);
                }
                x += Constants.DUCKY_SPEED;
                direction = "right";
                updateHitboxRight(x);
            }
            //moving right and down
            else if (kh.getRightPres() == true && kh.getLeftPres() != true
            && kh.getSpacePres() != true && kh.getDownPres() == true) {
                if (!Collisions.canMoveHere(hitbox.x + 13, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    x = x - Constants.DUCKY_SPEED;
                    updateHitbox(x, y);
                    System.out.println("OOF");
                }
                x += Constants.DUCKY_SPEED;
                direction = "right";
                updateHitboxRight(x);
            }
            //moving right
            else if (kh.getRightPres() == true && kh.getLeftPres() != true
            && kh.getSpacePres() != true && Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                x += Constants.DUCKY_SPEED;
                direction = "right";
                updateHitboxRight(x);
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
                updateHitboxLeft(x);
            }
        }

        public void update() {
            duckyMovementAndHitbox();
            setAni();
            updateAni();
            /*float xIndex = x / Constants.TILES_SIZE;
		    float yIndex = y / Constants.TILES_SIZE;
            System.out.println("x: " + xIndex);
            System.out.println("y: " + yIndex);*/
        }
        public void draw(Graphics g) {
            g.drawImage(duckAni[spriteRow][spriteLoop], x, y, width, height, null);
            drawHitbox(g);
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
