package entity;
import java.io.IOException;
import javax.imageio.ImageIO;
import handler.KeyHandler;
import util.Constants;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

    public class Ducky extends Entity {
        BufferedImage duckSprite;
        BufferedImage[][] duckAni = new BufferedImage[3][2];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 25;

        KeyHandler kh = new KeyHandler();

        String direction = "";

        public Ducky(KeyHandler kh, int x, int y) {
            super(x, y);
            getPlayerImage();
            loadAni();
            this.kh = kh;
        }

        private void getPlayerImage() {
            try {
                duckSprite = ImageIO.read(getClass().getResourceAsStream("/res/duckySprite.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void loadAni() {
            for (int i =0; i < duckAni.length; i++) {
                for (int j = 0; j < duckAni[i].length; j++) {
                    duckAni[i][j] = duckSprite.getSubimage(j * 16, i * 16, 16, 16);
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
                default: 
                    spriteCol = 0;
                    spriteRow = Constants.DUCKY_IDLE;
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
                if (spriteLoop >= spriteCol) {
                    spriteLoop = 0;
                }
            }
        }

        private void duckyMovement() {
            if (kh.getUpPres() == true && kh.getDownPres() != true){
                y -= Constants.DUCKY_SPEED;
                direction = "up";
            }
            else if (kh.getDownPres() == true && kh.getUpPres() != true) {
                y += Constants.DUCKY_SPEED;
                direction = "down";
            }
            else if (kh.getLeftPres() == true && kh.getRightPres() != true) {
                x -= Constants.DUCKY_SPEED;
                direction = "left";
            }
            else if (kh.getRightPres() == true && kh.getLeftPres() != true) {
                x += Constants.DUCKY_SPEED;
                direction = "right";
            }
            if (kh.getRightPres() != true && kh.getLeftPres() != true 
            && kh.getDownPres() != true && kh.getUpPres() != true) {
                direction = "";
            }
        }

        public void update() {
            duckyMovement();
            setAni();
            updateAni();
        }
        public void draw(Graphics g) {
            g.drawImage(duckAni[spriteRow][spriteLoop], x, y, 32, 32, null);
        }

        public void resetDir() {
            kh.downPressed = false;
            kh.upPressed = false;
            kh.leftPressed = false;
            kh.rightPressed = false; 
        }
    }
