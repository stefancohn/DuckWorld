package handler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public Boolean upPressed = false;
    public Boolean downPressed = false;
    public Boolean leftPressed = false;
    public Boolean rightPressed = false;
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_W) {
            upPressed = false;
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
    
}
