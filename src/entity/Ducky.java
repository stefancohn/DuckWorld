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
    KeyHandler kh = new KeyHandler();
    int xPos = 0;
    int yPos = 0;

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
                duckAni[i][j] = duckSprite.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
    }

    public void update() {
        if (kh.upPressed == true){
            yPos -= Constants.DUCKY_SPEED;
        }
        if (keyH.downPressed == true) {
            y += speed;
            direction = "down";
        }
        if (keyH.leftPressed == true) {
            x -= speed;
            direction = "left";
        }
        if (keyH.rightPressed == true) {
            x += speed;
            direction = "right";
        }
    }
    public void draw(Graphics g) {
        g.drawImage(duckAni[1][1], xPos, yPos, 32, 32, null);
    }
}
