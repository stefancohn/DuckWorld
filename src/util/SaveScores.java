package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import statemanager.PlayingScene;

public class SaveScores {
    public static ArrayList<Integer> highscores;

    public static void saveScore() {
        SaveScores.highscores = readScores(); //adds .txt file to arraylist
        cleanScores(); //clean up the scores
        int holder = (int) (PlayingScene.gameScore * 5);
        if (isTop5(holder)) { //check if top 5
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("highscoresData.txt", true))){ //append score in txt file
                String gameScore = String.valueOf(holder);
                bw.write(gameScore); //write down the score for future reference
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cleanScores(); //clean them up at the end
    }

    private static Boolean isTop5(int score) { //determines if a number is top 5 and also adds it to our arraylist 
        if (SaveScores.highscores.size() <= 4) { //check if less than 5 numbers, just add automatically
            highscores.add(score);
            return true; 
        }

        for (int i = 0; i < highscores.size(); i++) { //add score if it is more than an existing score
            if (score > highscores.get(i)) {
                highscores.add(score);
                return true;
            }
        }

        return false;
    } 

    private static ArrayList<Integer> readScores() { //run this first to get the text file into highscores arraylist
        ArrayList<Integer> highscores = new ArrayList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader("highscoresData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                highscores.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highscores;
    }

    private static void cleanScores() { //clean scores up
        Collections.sort(SaveScores.highscores, Collections.reverseOrder());
        if (SaveScores.highscores.size() > 5) {
            for (int i = SaveScores.highscores.size() - 1; i > 4; i--) {
                SaveScores.highscores.remove(i);
            }
        }
    }
}