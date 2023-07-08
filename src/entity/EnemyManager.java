package entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import levels.LevelManager;
import main.Game;
import statemanager.PlayingScene;
import util.Collisions;
import util.Constants;
import util.LoadSave;

public class EnemyManager {
    ArrayList<Goose> enemies = new ArrayList<Goose>(); //array list to hold our enemy geese

    int[][] spawnPoints; //spawn points for the spawnGooseDefault method

    int width = 36; //width and height for geese
    int height = 36;

    Random spawnGooseChance = new Random();

    Ducky duck = Game.game.getDucky();

    LevelManager levelManager;
    int[][] levelData; //recieves levelData from levelManager

    ArrayList<DuckyProjectile> projectiles = new ArrayList<DuckyProjectile>();  //arraylist to keep track of projectiles

    public EnemyManager(LevelManager levelManager) {
        this.levelManager = levelManager;
        levelData = levelManager.getCurrentLevel().getLevelData();
        spawnGooseDefault(); 
    }

    public void spawnGooseDefault() { //this method spawns the goose for the default levle that shows when the game starts
        spawnPoints = LoadSave.getLevelDataBlue("res/levelOne.png");
        for (int i = 0; i < spawnPoints.length; i++) { 
            for (int j = 0; j < spawnPoints[i].length; j++) { //gets level data regarding blue squares, ones with value 1 spawn a goose
                int value = spawnPoints[i][j];
                if (value == 1) {
                    enemies.add(new Goose(j * Constants.TILES_SIZE, i * Constants.TILES_SIZE, width, height, levelData));
                }
            }
        }
    }

    public void spawnGooseRandom() {
        int[][] levelData = levelManager.getCurrentLevel().getLevelData();
        for (int i = 1; i <levelData.length; i++) {
            int col49 = levelData[i][49]; //get three blocks on different columns
            int col48 = levelData[i][48];
            int col47 = levelData[i][47];
            if ((col49 == 0 || col49 == 3) && (col48 == 0 || col48 == 3) && (col47 == 0 || col47 == 3) &&  //makes sure the blocks under are ground
            levelData[i-1][47] == 4 && levelData[i-1][48] == 4 && levelData[i-1][49] == 4 &&
            levelData[i-2][47] == 4 && levelData[i-2][48] == 4 && levelData[i-2][49] == 4) { //ensures the three blocks above the spawn point are blank up two rows
                int randomVal = spawnGooseChance.nextInt(101);
                if (randomVal <= 6 + (int) PlayingScene.gameScore) { //6% chance an enemy spawns if spawn conditions are met, increases with difficulty 
                    enemies.add(new Goose(47 * Constants.TILES_SIZE, i * Constants.TILES_SIZE - 10 - height, width, height, levelData));
                }
            }
        }
    }

    public void callXOffsetGoose() {
        for (Goose goose : enemies) {
            goose.xOffsetForConstantMove(Constants.MOVE_SCREEN_RIGHT_LENGTH * Constants.TILES_SIZE);
        }
    }

    public void removeGoose() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).gooseDead()) {
                enemies.remove(i);
            }
        }
    }

    public void duckyAndGooseCollision() {
        for (int i = 0; i < enemies.size(); i++) {
            //check for collision between duck and goose 
            if (enemies.size() > 0 && Collisions.entityCollide(duck.hitbox, enemies.get(i).hitbox)) {
                duck.setIsDead(true); // set isDead flag to true, thus killing ducky
            }
        }
    }
    public void gooseAndProjectileCollision() { 
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < projectiles.size(); j++) {
                if (Collisions.entityCollide(enemies.get(i).hitbox, projectiles.get(j).hitbox)) {
                    projectiles.get(j).collided = true;
                    enemies.get(i).isDead = true;
                    PlayingScene.gameScore+= .2001;
                }
            }
        }
    }

    public void createProjectiles() {
        //makes sure only one projectile created at end of animation instead of many when attacking
        if (duck.isAttacking && duck.spriteLoop == 3 && duck.aniTick == 7 && !duck.isAttackingLeft) { 
            projectiles.add(new DuckyProjectile(duck.hitbox.x + duck.hitbox.width + 4, duck.hitbox.y + 6, 10, 10, true, levelData));
        } else if (duck.isAttacking && duck.spriteLoop == 3 && duck.aniTick == 7 && duck.isAttackingLeft) {
            projectiles.add(new DuckyProjectile(duck.hitbox.x, duck.hitbox.y + 6, 10, 10, false, levelData));
        }
    }
    public void removeProjectiles() { //removes projectiles if collided flag is raised
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).collided) { 
                projectiles.remove(i);
            }
        }
    }



    public void update() {
        createProjectiles();
        duckyAndGooseCollision();
        gooseAndProjectileCollision();
        for (Goose goose : enemies) { goose.update(); }
        for (int i = 0; i < projectiles.size(); i++) { projectiles.get(i).update(); }
        removeGoose();
        removeProjectiles();
    }
    public void draw(Graphics g) {
        for (Goose goose : enemies) {
            goose.draw(g);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).draw(g);
        }
    }
}