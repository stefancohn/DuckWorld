package util;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String DUCKY_ATLAS = "res/duckySprite.png";
    public static final String LEVEL_ATLAS = "res/mapSprite.png";

    public static final String START_LEVEL = "res/levelOne.png";
    public static final String OBSTACLE_SEQUENCES = "res/levelSequences.png";

    //method to pull BufferedImage from file 
    public static BufferedImage getSpriteAtlas(String file) {
        BufferedImage img = null;
            try {
                img = ImageIO.read(LoadSave.class.getResourceAsStream("/" + file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return img;
        }
    
        //grabs the Red value of each tile of level map and rerturns it as 
        //a 2d array
    public static int[][] getLevelDataRed(String file) {
        BufferedImage img = getSpriteAtlas(file);
        int[][] levelData = new int[img.getHeight()] [img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 6) {
                    value = 4;
                }
                levelData[i][j] = value;
            }
        }
            return levelData;
        }
    //same method as above but works with images instead of files
    public static int[][] getLevelDataRedImg(BufferedImage img) {
        int[][] levelData = new int[img.getHeight()] [img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 6) {
                    value = 4;
                }
                levelData[i][j] = value;
            }
        }
            return levelData;
        }
    }
