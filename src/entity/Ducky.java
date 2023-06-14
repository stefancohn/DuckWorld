package entity;
import handler.KeyHandler;
import util.Constants;
import util.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

    public class Ducky extends Entity {
        BufferedImage duckSprite;

        public static int duckDimensionsIdle = 32; 
        public static int duckDimensionsSide = 18;

        BufferedImage[][] duckAni = new BufferedImage[5][4];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 15;

        KeyHandler kh = new KeyHandler();

        String direction = "";

        Boolean isAttacking = false;

        public Ducky(KeyHandler kh, int x, int y, int width, int height) {
            super(x, y, width, height);
            duckSprite = LoadSave.getSpriteAtlas(LoadSave.DUCKY_ATLAS);
            loadAni();
            this.kh = kh;
            initializeHitbox(x, y, width, height);
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
                    hitbox.x += 10;
                    hitbox.width = Ducky.duckDimensionsSide;
                    break;
                case "left": 
                    spriteCol = 2;
                    spriteRow = Constants.DUCKY_LEFT;
                    hitbox.x += 6;
                    hitbox.width = Ducky.duckDimensionsSide;
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
                    hitbox.x=x;
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

        private void duckyMovement() {
            //reset attack button
            if (kh.getSpacePres() == true && kh.getRightPres() != true && kh.getLeftPres() != true 
            && kh.getDownPres() != true && kh.getUpPres() != true && isAttacking != true) {
                kh.spacePressed = false;
            }
            //idle
            if (kh.getRightPres() != true && kh.getLeftPres() != true 
            && kh.getDownPres() != true && kh.getUpPres() != true && kh.getSpacePres() != true) {
                direction = "";
            }
            //hit box and movement fix
            if (kh.getRightPres() == true && kh.getLeftPres() == true) {
                direction = "";
            }
            //moving up
            if (kh.getUpPres() == true && kh.getDownPres() != true 
            && kh.getSpacePres() != true){
                y -= Constants.DUCKY_SPEED;
                hitbox.y = y;
                direction = "up";
            }
            //moving down
            else if (kh.getDownPres() == true && kh.getUpPres() != true
            && kh.getSpacePres() != true) {
                direction = "down";
                if (y < 224) { //224
                    y+= Constants.DUCKY_SPEED;
                    hitbox.y = y;
                }
            }
            //moving left
            else if (kh.getLeftPres() == true && kh.getRightPres() != true
            && kh.getSpacePres() != true) {
                x -= Constants.DUCKY_SPEED;
                hitbox.x = x;
                direction = "left";
            }
            //moving right
            else if (kh.getRightPres() == true && kh.getLeftPres() != true
            && kh.getSpacePres() != true) {
                x += Constants.DUCKY_SPEED;
                hitbox.x = x;
                direction = "right";
            }
            //attack right
            else if (kh.getSpacePres() == true && kh.getRightPres() == true 
            && kh.getLeftPres() != true) {
                isAttacking = true;
                direction = "attackingRight";
            }
            //attack left
            else if (kh.getSpacePres() == true && kh.getLeftPres() == true
            && kh.getRightPres() != true) {
                isAttacking = true;
                direction = "attackingLeft";
            }
        }

        public void update() {
            duckyMovement();
            setAni();
            updateAni();
            System.out.println(direction);
        }
        public void draw(Graphics g) {
            g.drawImage(duckAni[spriteRow][spriteLoop], x, y, 32, 32, null);
            drawHitbox(g);
        }

        public void resetDir() {
            kh.downPressed = false;
            kh.upPressed = false;
            kh.leftPressed = false;
            kh.rightPressed = false; 
            isAttacking = false;
        }
    }
