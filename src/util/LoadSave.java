package util;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String DUCKY_ATLAS = "res/duckySprite.png";
    public static final String LEVEL_ATLAS = "res/mapSprite.png";

    public static BufferedImage getSpriteAtlas(String file) {
        BufferedImage img = null;
            try {
                img = ImageIO.read(LoadSave.class.getResourceAsStream("/" + file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return img;
        }
    }
