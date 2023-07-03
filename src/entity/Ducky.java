package entity;
import handler.KeyHandler;
import util.Collisions;
import util.Constants;
import util.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

    public class Ducky extends Entity {
        BufferedImage duckSprite;

        //duck dimensions
        public static int duckDimensionsIdle = 37; 
        public static int duckDimensionsSide = 21;

        //animation variables
        BufferedImage[][] duckAni = new BufferedImage[6][4];
        int spriteLoop = 0;
        int spriteRow = 0;
        int spriteCol = 0;
        int aniTick, aniSpeed = 15;
        String direction = "";
        Boolean isAttacking = false; //for tracking attack animation properly 
        Boolean isAttackingLeft = false;
        Boolean isDead = false; //for tracking death animation properly

        public KeyHandler kh = new KeyHandler();

        //gravity and friction variables
        Boolean jump = false;
        Boolean inAir = true;
        Boolean airWallCollision = false;
        public int airSpeed = -6;
        public int jumpHeight = -100;
        double friction = 0.1;
        int yPosBeforeJump;
        
        int[][] levelData; //recieves levelData from PlayingScene's levelManager

        public Ducky(KeyHandler kh, int x, int y, int width, int height) {
            super(x, y, width, height);
            this.kh = kh;
            initializeHitbox(x, y, width, height - 2);
            duckSprite = LoadSave.getSpriteAtlas(LoadSave.DUCKY_ATLAS);
            loadAni();
        }

        public void initiateLevelData(int[][] levelData) {
            this.levelData = levelData;
        }

        private void loadAni() {
            for (int i =0; i < duckAni.length; i++) {
                for (int j = 0; j < duckAni[i].length; j++) {
                    duckAni[i][j] = duckSprite.getSubimage(j * 16, i * 16, Constants.TILES_SIZE, Constants.TILES_SIZE);
                }
            }
        }

        //set the animation to be shown
        private void setAni() {
            int startAni = spriteRow;
            switch (direction) { //changes sprite col to the total amount of animations for direction and selects the row in which they are in the sprite sheet
                case "right":
                    spriteCol = 2;
                    spriteRow = Constants.DUCKY_RIGHT;
                    break;
                case "left": 
                    spriteCol = 2;
                    spriteRow = Constants.DUCKY_LEFT;
                    break;
                case "attackingRight":
                    spriteCol = 4;
                    spriteRow = Constants.DUCKY_ATTACK_RIGHT;
                    break;
                case "attackingLeft":
                    spriteCol = 4;
                    spriteRow = Constants.DUCKY_ATTACK_LEFT;
                    break;
                case "death": 
                    spriteCol = 4;
                    spriteRow = Constants.DUCKY_DEATH;
                    updateHitboxSide(duckDimensionsIdle);
                default: 
                if (!isDead) { //so it doesn't activate when dead
                    spriteCol = 0;
                    spriteRow = Constants.DUCKY_IDLE;
                    updateHitboxSide(duckDimensionsIdle);
                    break;
                }
            }
            //restarts the animation from the start when animations switch
            if (spriteRow != startAni) {
                aniTick = 0;
                spriteLoop = 0;
            }
        }

        private void updateAni() {
            aniTick++;  //update ani tick
            if (aniTick >= aniSpeed) {  //once anitick is greater than desired speed reset it and go to next sprite in the animation
                aniTick = 0;
                spriteLoop++;
                if (isAttacking && spriteLoop == 4) { //this is so that when space is pressed, the attack animation runs through in full, it is stopeed, and anispeed is back to defaul
                    kh.spacePressed = false;
                    isAttacking = false;
                    isAttackingLeft = false;
                    aniSpeed = 15;
                }
                if (isDead && spriteLoop >= 3) {
                    spriteLoop = 3;
                }
                else if (spriteLoop >= spriteCol) { //restart animation when reaches end of animation columns from sprite sheet
                    spriteLoop = 0;
                }
            }
        }

        private void duckyMovementAndHitbox() {
            //change into idle pos
            if (!kh.getRightPres() && !kh.getLeftPres()
             && !kh.getSpacePres() && !kh.getDownPres() && !kh.getUpPres() && !isDead) {
                //move pos back when going to idle pos
                direction = "";
                if (inAir && !jump && !Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    if (!airWallCollision) {
                        hitbox.x = Collisions.getXposNextToWallRightIdleInAir(hitbox); 
                        airWallCollision = true;
                    }
                }
                else if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXposNextToWallRightIdle(hitbox);
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }
            //hit box and movement fix
            if (kh.getRightPres() && kh.getLeftPres()) {
                direction = "";
                //move pos back when going to idle pos
                if (!Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXposNextToWallRightIdle(hitbox);
                } else if (!Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }
            //moving left
            if (kh.getLeftPres() && !kh.getRightPres() && !kh.getSpacePres()) {
                direction = "left";
                updateHitboxSide(duckDimensionsSide);
                if (Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)){
                    hitbox.x -= Constants.DUCKY_SPEED; //if ducky can move, we do move him
                } else {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox); //if not, we get his exact position next to a block
                }
            }//attack left
            else if (kh.getSpacePres() && kh.getLeftPres() && !kh.getRightPres()) {
                isAttacking = true;
                direction = "attackingLeft";
                aniSpeed = 8; //change ani speed to make animation faster
                isAttackingLeft = true;
                updateHitboxSide(duckDimensionsSide);
                //these commands make sure we can move, jump, and shoot all at the same time
                if (kh.getUpPres()) {
                    jump();
                }
                if (Collisions.canMoveHere(hitbox.x - Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)){
                    hitbox.x -= Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXPosNextToWallLeft(hitbox);
                }
            }
            //moving right
            if (kh.getRightPres() && !kh.getLeftPres()
            && !kh.getSpacePres()) {
                direction = "right";
                updateHitboxSide(duckDimensionsSide);
                if (Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x += Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXposNextToWallRightMoving(hitbox);
                }
            } //attack right
            else if (kh.getSpacePres() && kh.getRightPres() && !kh.getLeftPres()) {
                isAttacking = true;
                direction = "attackingRight";
                aniSpeed = 8;
                updateHitboxSide(duckDimensionsSide);
                if (kh.getUpPres()) {
                    jump();
                }
                if (Collisions.canMoveHere(hitbox.x + Constants.DUCKY_SPEED, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                    hitbox.x += Constants.DUCKY_SPEED;
                } else {
                    hitbox.x = Collisions.getXposNextToWallRightMoving(hitbox);
                }
            }
            //regular attack
            if (kh.getSpacePres() && !kh.getRightPres() && !kh.getLeftPres() && !isAttackingLeft) {
                isAttacking = true;
                direction = "attackingRight";
                aniSpeed = 8;
                updateHitboxSide(duckDimensionsSide);
            }
            //reset attack button
            if (kh.getSpacePres() && !kh.getRightPres() && !kh.getLeftPres() 
            && !kh.getDownPres() && !kh.getUpPres() && !isAttacking) {
                kh.spacePressed = false;
            }

            //jump
            if (kh.getUpPres() && !kh.getDownPres() && !inAir && !kh.getSpacePres()){
                if (!jump) {
                    jump();
                }
            }
            if (!inAir) {
                yPosBeforeJump = hitbox.y;
            }
            //jump checks
            if (inAir && jump) {
                if (Collisions.canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)){ 
                    hitbox.y += airSpeed; //if can move up add speed
                    if (hitbox.y < yPosBeforeJump + jumpHeight) { //once reaches jump height, no longer jump
                        jump = false; 
                        kh.upPressed = false;
                    }
                }
                else { //if is in air and jump but can't move up: 
                    hitbox.y = Collisions.getYPosCeilingAbove(hitbox);
                    jump = false;
                    kh.upPressed = false;
                }
            }
            //gravity
            if (!Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += Constants.GRAVITY;
                inAir = true;
            } else if (Collisions.isOnFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                inAir = false;
                jump = false;
                hitbox.y = Collisions.getYposFloorBelow(hitbox);
                airWallCollision = false;
                yPosBeforeJump = hitbox.y;
            }
        }

        private void jump() {
            inAir = true;
            jump = true;
        }

        public void xOffsetForConstantMove(int xOffset) {
            hitbox.x -= xOffset;
        }

        public void duckyDead() {
            if (Collisions.touchedLava(hitbox.x, hitbox.y, levelData)) {
                isDead =true; 
            }
            if (hitbox.x <= 0) {
                isDead = true;
            }
            if (isDead) {
                direction = "death";
                //perform death animation 
               // System.out.println("DEAD");
            }
        }

        public void setIsDead(Boolean flag) {
            this.isDead = flag;
        }

        public void update() {
            duckyMovementAndHitbox();
            duckyDead();
            setAni();
            updateAni();
        }
        public void draw(Graphics g) {
            if (direction == "right" || direction == "attackingRight") {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x - 10, hitbox.y, width, height, null);
                //drawHitbox(g);
            } else if (direction == "left" || direction == "attackingLeft") {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x - 8, hitbox.y, width, height, null);
                //drawHitbox(g);
            } else {
                g.drawImage(duckAni[spriteRow][spriteLoop], hitbox.x, hitbox.y, width, height, null);
                //drawHitbox(g);
            }
        }

        public void resetDir() { //for lost focus glitch
            kh.downPressed = false;
            kh.upPressed = false;
            kh.leftPressed = false;
            kh.rightPressed = false;
            direction = ""; 
            isAttacking = false;
        }
    }