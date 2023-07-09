package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import handler.MouseHandler;
import main.Game;
import statemanager.PlayingScene;
import util.Constants;
import util.LoadSave;

public class PauseOverlay {
    MouseHandler mh;
    BufferedImage pauseOverlay;

    int xPlacementForOverlay;
    int yPlacementForOverlay;

    //buttons stuffs
    BufferedImage[][] pauseButtons = new BufferedImage[2][2];
    int xPlacementForButtons;
    int playButtonY = 150;
    int quitButtonY = 275;
    int playButtonShown = 0;
    int quitButtonShown = 0;
    int buttonWidth = 200;
    int buttonHeight = 50;

    public PauseOverlay(MouseHandler mh) {
        this.mh = mh;
        initialzePauseOverlay();
        initializePauseButtons();
    }

    public void initialzePauseOverlay() {
        pauseOverlay = LoadSave.getSpriteAtlas("/res/pauseScreen.png");
        xPlacementForOverlay = (Constants.GAME_WIDTH/2) - (pauseOverlay.getWidth()/2);
        yPlacementForOverlay = (Constants.GAME_HEIGHT/2) - (pauseOverlay.getHeight()/2) - 20;
    }

    public void initializePauseButtons() {
        xPlacementForButtons = xPlacementForOverlay + 25;

        BufferedImage img = LoadSave.getSpriteAtlas("/res/pauseButtons.png");
        for (int i = 0; i < pauseButtons.length; i++) {
            for (int j = 0; j < pauseButtons[i].length; j++) {
                pauseButtons[i][j] = img.getSubimage(j * buttonWidth, i * buttonHeight, buttonWidth, buttonHeight);
            }
        }
    }

    public void mouseMovement() {
        //resume button function and bounds
        if (mh.x > xPlacementForButtons && mh.x < xPlacementForButtons + buttonWidth
        && mh.y > playButtonY && mh.y < playButtonY + buttonHeight) {
            playButtonShown = 1;
            if (mh.clicked) {
                Game.getGame().getPanel().kh.pause = false;
                PlayingScene.unpaused = true;
            }
        } else {
            playButtonShown = 0;
        }

        //quit button function and bounds
        if (mh.x > xPlacementForButtons && mh.x < xPlacementForButtons + buttonWidth
        && mh.y > quitButtonY && mh.y < quitButtonY + buttonHeight) {
            quitButtonShown = 1;
            if (mh.clicked) {
                System.exit(0);
            }
        } else {
            quitButtonShown = 0;
        }
    }

    public void update() {
        mouseMovement();
    }
    public void draw(Graphics g) {
        g.drawImage(pauseOverlay, xPlacementForOverlay, yPlacementForOverlay, 250, 450, null);
        g.drawImage(pauseButtons[0][playButtonShown], xPlacementForButtons, playButtonY, null);
        g.drawImage(pauseButtons[1][quitButtonShown], xPlacementForButtons, quitButtonY, null);
    }
}