package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import util.Constants;
import util.LoadSave;

public class GameFrame extends JFrame {

    GameFrame(GamePanel panel) {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Duck World");
        this.setLocationRelativeTo(null);

        this.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) { //fixes bug where inputs remain despite losing focus
               panel.getGame().windowFocusLost();
            }
            
        });
    }
}