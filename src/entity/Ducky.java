package entity;
import java.io.IOException;
import javax.imageio.ImageIO;
import handler.KeyHandler;
import util.Constants;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

    public class Ducky {
        BufferedImage duckSprite;
        BufferedImage[][] duckAni = new BufferedImage[3][2];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 30;

        KeyHandler kh = new KeyHandler();

        int xPos = 0;
        int yPos = 0;
        String direction = "";

        public Ducky(KeyHandler kh) {
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
            if (kh.upPressed == true){
                yPos -= Constants.DUCKY_SPEED;
                direction = "up";
            }
            if (kh.downPressed == true) {
                yPos += Constants.DUCKY_SPEED;
                direction = "down";
            }
            if (kh.leftPressed == true) {
                xPos -= Constants.DUCKY_SPEED;
                direction = "left";
            }
            if (kh.rightPressed == true) {
                xPos += Constants.DUCKY_SPEED;
                direction = "right";
            }
            if (kh.rightPressed != true && kh.leftPressed != true 
            && kh.downPressed != true && kh.upPressed != true) {
                direction = "";
            }
        }

        public void update() {
            duckyMovement();
            setAni();
            updateAni();
            //System.out.println(spriteLoop);
        }
        public void draw(Graphics g) {
            g.drawImage(duckAni[spriteRow][spriteLoop], xPos, yPos, 32, 32, null);
        }
    }
