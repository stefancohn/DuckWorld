package main;

public class Main {
    public static void main(String[] args) {
        Game game = Game.getGame();
        game.startGameThread();
    }
}