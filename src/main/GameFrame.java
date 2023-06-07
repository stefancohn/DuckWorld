package main;
import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {
        this.setSize(Game.Constants.SCREEN_WIDTH, Game.Constants.SCREEN_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Duck World");
    }
}
