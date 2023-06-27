package handler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import statemanager.PlayingScene;

public class KeyHandler implements KeyListener {
    public Boolean upPressed = false;
    public Boolean downPressed = false;
    public Boolean leftPressed = false;
    public Boolean rightPressed = false;
    public Boolean spacePressed = false;
    public Boolean pause = false;
    public String direction = "";

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_W) {
            upPressed = true;
            direction = "up";
        }
        if (i == KeyEvent.VK_S) {
            downPressed = true;
            direction = "down";
        }
        if (i == KeyEvent.VK_D) {
            rightPressed = true;
            direction = "right";
        }
        if (i == KeyEvent.VK_A) {
            leftPressed = true;
            direction = "left";
        }
        if (i == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (i == KeyEvent.VK_ESCAPE) {
            //to make sure the restart counter happens whne the game is unpaused via escape
            if (pause) {
                pause = !pause;
                PlayingScene.unpaused = true;
            } else {
            pause = !pause; 
        }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_W) {
            //upPressed = false;
            direction = "";
        }
        if (i == KeyEvent.VK_S) {
            downPressed = false;
            direction = "";
        }
        if (i == KeyEvent.VK_D) {
            rightPressed = false;
            direction = "";
        }
        if (i == KeyEvent.VK_A) {
            leftPressed = false;
            direction = "";
        }
    }
    public Boolean getUpPres() { return upPressed; }
    public Boolean getDownPres() { return downPressed; }
    public Boolean getRightPres() { return rightPressed; }
    public Boolean getLeftPres() { return leftPressed; }
    public Boolean getSpacePres() {return spacePressed; }
    public Boolean getPause() {return pause;}
}
