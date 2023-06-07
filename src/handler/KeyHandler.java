package handler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public Boolean upPressed = false;
    public Boolean downPressed = false;
    public Boolean leftPressed = false;
    public Boolean rightPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (i == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (i == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (i == KeyEvent.VK_A) {
            leftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (i == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (i == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (i == KeyEvent.VK_A) {
            leftPressed = false;
        }
    }
    
}
