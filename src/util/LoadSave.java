package util;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String DUCKY_ATLAS = "res/duckySprite.png";
    public static final String LEVEL_ATLAS = "res/mapSprite.png";
    public static final String LEVEL_ONE = "res/levelOne.png";

    public static BufferedImage getSpriteAtlas(String file) {
        BufferedImage img = null;
            try {
                img = ImageIO.read(LoadSave.class.getResourceAsStream("/" + file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return img;
        }

    public static int[][] getLevelData() {
        int[][] levelData = new int[Constants.TILES_IN_HEIGHT] [Constants.TILES_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LEVEL_ONE);

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 5) {
                    value = 0;
                }
                levelData[i][j] = value;
            }
        }
            return levelData;
        }
    }
