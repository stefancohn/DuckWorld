package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.Constants;
import util.LoadSave;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite = new BufferedImage[5]; 
    Level levelOne;

    public LevelManager(Game game){
        this.game = game;
        importLevelSprite();
        levelOne = new Level(LoadSave.getLevelData());
    }

    public void importLevelSprite() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        for (int i = 0; i < 5; i++) {
            levelSprite[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    public Level getCurrentLevel() {
        return levelOne;
    }

    public void update() {

    }
    public void draw(Graphics g) {
       for (int i = 0; i < Constants.TILES_IN_HEIGHT; i++) 
            for (int j = 0; j < Constants.TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], j * Constants.TILES_SIZE_DEF, i * Constants.TILES_SIZE_DEF, null);
        }
        
    }
}
