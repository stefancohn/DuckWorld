package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.LoadSave;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite = new BufferedImage[4]; 

    public LevelManager(Game game){
        this.game = game;
        importLevelSprite();
    }

    public void importLevelSprite() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        for (int i = 0; i < 4; i++) {
            levelSprite[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    public void update() {

    }
    public void draw(Graphics g) {
        g.drawImage(levelSprite[3], 500, 500, 20, 20, null);
    }
}
