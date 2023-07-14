package ui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import handler.MouseHandler;
import main.Game;
import util.Constants;
import util.LoadSave;

public class VolumeButton {
    MouseHandler mh;
    Boolean toggle = false;
    Boolean stopClick = true;

    BufferedImage[][] volumeButtons = new BufferedImage[2][2]; //holds sprites
    int volumeHovered = 0; //update this int to display greyed out icon when hovered
    int volumeSprite = 0; //update this to show either the muted or default icon 

    public VolumeButton(MouseHandler mh) {
        this.mh = mh;
        loadButton();
    }

    private void loadButton() { //put buttons into array 
        BufferedImage img = LoadSave.getSpriteAtlas("/res/volumeToggle.png");
        for (int i = 0; i < volumeButtons.length; i++) {
            for (int j = 0; j < volumeButtons[i].length; j++) {
                volumeButtons[i][j] = img.getSubimage(j * 230, i * 170, 230, 170);
            }
        }
    }

    private void mouseMovement() { //track mousemovement to properly update volume icon and mute the game when selected
        if (mh.x > 0 && mh.x < 50 && mh.y > Constants.GAME_HEIGHT - 50 && mh.y < Constants.GAME_HEIGHT) {
            volumeHovered = 1;
            if (mh.clicked && stopClick && !toggle) {
                iconMuted();
            } else if (mh.clicked && stopClick && toggle) {
                iconUnmuted();
            }
        } else {
            volumeHovered = 0;
            stopClick = true;
        }
    }

    private void iconMuted() {
        volumeSprite = 1;
        Game.game.getAudioPlayer().setVolume(0f);
        toggle = true;
        stopClick = false;
    }
    private void iconUnmuted() {
        volumeSprite = 0;
        Game.game.getAudioPlayer().setVolume(0.8f);
        toggle = false;
        stopClick = false;
    }

    public void update(){
        mouseMovement();
    }

    public void draw(Graphics g){
        //draw icon
        g.drawImage(volumeButtons[volumeSprite][volumeHovered], 0, Constants.GAME_HEIGHT - 50, 50, 50, null);
    }
}
