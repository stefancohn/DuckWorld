package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import entity.Ducky;
import handler.KeyHandler;
import handler.MouseHandler;
import util.Constants;

public class GamePanel extends JPanel {
    KeyHandler kh = new KeyHandler();
    MouseHandler mh = new MouseHandler();
    Ducky duck = new Ducky(kh);

    public GamePanel() {
        //set up game panel
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.addMouseListener(mh);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        duck.draw(g);
        g.dispose();
    }
}
