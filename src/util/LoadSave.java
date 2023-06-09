package util;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String DUCKY_ATLAS = "res/duckySprite.png";
    
    public static BufferedImage getSpriteAtlas(String file) {
        BufferedImage duckSprite = null;
            try {
                duckSprite = ImageIO.read(LoadSave.class.getResourceAsStream("/" + file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return duckSprite;
        }
    }
