package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import handler.KeyHandler;
import handler.MouseHandler;

public class GamePanel extends JPanel {
    KeyHandler kh = new KeyHandler();
    MouseHandler mh = new MouseHandler();
    BufferedImage duckSprite;

    public GamePanel() {
        //set up game panel
        this.setPreferredSize(new Dimension(Game.Constants.SCREEN_WIDTH, Game.Constants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.addMouseListener(mh);
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            duckSprite = ImageIO.read(getClass().getResourceAsStream("/res/duckySprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(duckSprite.getSubimage(0, 0, 16, 16), 0, 0, 32, 32, null);

        g.dispose();
    }
}
