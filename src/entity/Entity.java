package entity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import handler.KeyHandler;
import util.Collisions;
import util.Constants;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Rectangle hitbox;
    protected boolean offsetApplied = false;


    public Entity(int x, int y, int width, int height) {
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height; 
    }

    public void initializeHitbox(int x, int y, int width, int height) {
        hitbox = new Rectangle(x, y, width, height);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void updateHitboxRight(int x) {
        hitbox.width = Ducky.duckDimensionsSide;
    }

    public void updateHitboxLeft(int x) {
        hitbox.width = Ducky.duckDimensionsSide;
    }
}
