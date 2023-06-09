package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import handler.MouseHandler;
import main.Game;
import util.Constants;
import util.LoadSave;
import util.SaveScores;

public class DeathSceneOverlay {
    MouseHandler mh;
    BufferedImage[][] deathSceneButtons = new BufferedImage[2][2]; //holds sprites 

    //button variables
    int buttonWidth = 275;
    int buttonHeight = 50; 
    int buttonPlacementX = (Constants.GAME_WIDTH/2) - (buttonWidth/2);

    //variables to keep track which butotn sprite to show 
    int quitButtonSprite = 0;
    int playAgainButtonSprite = 0;

    VolumeButton volumeButton = new VolumeButton(Game.game.getPanel().getMouseHandler());

    public DeathSceneOverlay(MouseHandler mh) {
        this.mh = mh; 
        loadButtons();
    }

    public void loadButtons() { //get button from image and place it into array
        BufferedImage img = LoadSave.getSpriteAtlas("/res/deathScreenButtons.png");
        for (int i = 0; i < deathSceneButtons.length; i++) {
            for (int j = 0; j < deathSceneButtons[i].length; j++) {
                deathSceneButtons[i][j] = img.getSubimage(j * 200, i * 50, 200, 50);
            }
        }
    }

    private void mouseMovement() { //track mouse movement to switch images when hovering and when user clicks
        //for play again button
        if (buttonPlacementX < mh.x && buttonPlacementX + buttonWidth > mh.x && 300 < mh.y && 300 + buttonHeight > mh.y) {
            playAgainButtonSprite = 1;
            if (mh.clicked) {
                Game.game.getDucky().defaultDucky();
                Game.game.changeState(Constants.SCENE_MENU);
            }
        } else {
            playAgainButtonSprite = 0;
        }

         //for quit button
         if (buttonPlacementX + 10 < mh.x && buttonPlacementX + 10 + buttonWidth - 20 > mh.x && 375 < mh.y && 375 + buttonHeight > mh.y) {
            quitButtonSprite = 1;
            if (mh.clicked) {
                System.exit(0);
            }
        } else {
            quitButtonSprite = 0;
        }
    }

    public void update() {
        mouseMovement();
        Game.game.getVolumeButton().update();
    }
    public void draw(Graphics g) {
        g.drawImage(deathSceneButtons[0][playAgainButtonSprite], buttonPlacementX, 300, buttonWidth, buttonHeight, null); //draw play again button
        g.drawImage(deathSceneButtons[1][quitButtonSprite], buttonPlacementX + 10, 375, buttonWidth - 20, buttonHeight, null); //draw quit button
        Game.game.getVolumeButton().draw(g); //draw volume button

        //draw highscores
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
        g.drawString("HIGHSCORES", 275, 120);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        for (int i = 0; i < SaveScores.highscores.size(); i++) {
            g.drawString((i + 1) + ") " + SaveScores.highscores.get(i).toString(), 365, 150 + i * 30);
        }
    }

}