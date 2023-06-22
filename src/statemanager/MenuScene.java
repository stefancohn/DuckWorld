package statemanager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import handler.MouseHandler;
import main.Game;
import util.Constants;
import util.LoadSave;

public class MenuScene extends Scene{
    MouseHandler mh;
    BufferedImage menuImage = LoadSave.getSpriteAtlas("res/menuScreen.png");
    BufferedImage[] playButton = new BufferedImage[2];
    int buttonSprite = 0; 

    public MenuScene(MouseHandler mh) {
        this.mh = mh;
        initializePlayButton();
    }

    public void initializePlayButton() {
        BufferedImage img = LoadSave.getSpriteAtlas("res/playButton.png");
        for (int i = 0; i < playButton.length; i++) {
            playButton[i] = img.getSubimage(100 * i, 0, 100, 50);
        }
    }

    public void mouseMovement() {
        //track if mouse is in bounds of play button
        if (mh.x > (Constants.GAME_WIDTH/2) - 200 && 
        mh.x < (((Constants.GAME_WIDTH/2) - 200) + 400) && mh.y > 200
        && mh.y < 300) {
            buttonSprite = 1;
            if (mh.clicked) {
                Game.game.changeState(Constants.SCENE_PLAYING);
            }
        } else {
            buttonSprite = 0;
        }
    }

    @Override
    public void update() {
        mouseMovement();
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(menuImage, 0, 0, null);
        g.drawImage(playButton[buttonSprite], (Constants.GAME_WIDTH/2) - 200, 200, 400, 100, null);
    }
    
}
