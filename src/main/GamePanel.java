package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import handler.KeyHandler;
import handler.MouseHandler;
import util.Constants;

public class GamePanel extends JPanel {
    KeyHandler kh = new KeyHandler();
    MouseHandler mh = new MouseHandler();
    Game game;

    public GamePanel(Game game) {
        //set up game panel
        this.setPreferredSize(new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.addMouseListener(mh);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void update() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
        g.dispose();
    }
}
