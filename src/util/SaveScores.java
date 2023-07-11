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
        SaveScores.highscores = readScores(); //adds score to file
        int holder = (int) (PlayingScene.gameScore * 5);
        if (isTop5(holder)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("highscoresData.txt", true))){ //append score in txt file
                String gameScore = String.valueOf(holder);
                bw.write(gameScore);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Boolean isTop5(int score) { //determines if a number is top 5 and also adds it to our arraylist 
        if (SaveScores.highscores.size() < 5) { //check if less than 5 numbers

            if (SaveScores.highscores.size() < 1) { //if nothing in array just add score, no checks
                highscores.add(score);
                return true; }

            for (int i = 0; i < SaveScores.highscores.size(); i++) { //find which score to replace 
                if (score > highscores.get(i)) {
                    highscores.add(i, score);
                } else {
                    highscores.add(score);
                }
            }
            return true;
        }

        for (int i = 0; i < highscores.size(); i++) { //find which score to replace if more than 5
            if (score > highscores.get(i)) {
                highscores.set(i, score);
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
        Collections.sort(highscores, Collections.reverseOrder());
        if (highscores.size() > 5) {
            for (int i = 5; i < highscores.size(); i++) {
                highscores.remove(i);
            }
        }
        return highscores;
    }
}