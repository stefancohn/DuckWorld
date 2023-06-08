package main;
import javax.swing.*;
import util.Constants;

public class GameFrame extends JFrame {

    GameFrame() {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Duck World");
    }
}
