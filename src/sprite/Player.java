package sprite;

import lombok.Data;
import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static manager.MapManager.MAP_WIDTH;



public class Player extends Sprite {
    private static double DURATION = 0.5;
    private static final int JUMP_SPEED = 3;

    private int animTick, animIdx, animSpeed = 20;
    private int playAction = 1;

    private boolean isFacingRight = true;
    private int[][] mapData;


    public Player(int x, int y, int w, int h, ImagesLoader imsLd) {
        super(x, y, w, h, imsLd, "idleRight");
    }

    public void loadMapData(int[][] mapData) {
        this.mapData = mapData;
    }

    public void update(){
        updateAnimationTick();
        updateAnimation();
        updatePosition();
        updateFalling();
        updateHitbox();
    }

    private void updateAnimation() {
        if (isFacingRight) setImage("idleRight");
        if (!isFacingRight) setImage("idleLeft");

        if (up && isFacingRight) setImage("jumpRight");
        if (up && !isFacingRight) setImage("jumpLeft");

        if (right) setRunningImages("runningRight");
        if (left) setRunningImages("runningLeft");
    }

    private void setRunningImages(String name) {
        setImage(name);
        loopImage(1, DURATION);
        if (name == "runningRight") isFacingRight = true;
        else isFacingRight = false;
    }



    private void updatePosition() {
        if (up && !isSolid(x, y - JUMP_SPEED, x + hitbox.width,y + hitbox.height)) {
            y -= JUMP_SPEED;
        }
        if (up && right && !isSolid(x + dx, y - JUMP_SPEED, x + hitbox.width,y + hitbox.height)) {
            x += dx; y -= JUMP_SPEED;
        }
        if (up && left && !isSolid(x - dx, y - JUMP_SPEED, x + hitbox.width,y + hitbox.height)) {
            x -= dx; y -= JUMP_SPEED;
        }
        if (right && !isSolid(x, y, x + hitbox.width + dx,y + hitbox.height)) {
            x += dx;
            isFacingRight = true;
        }
        if (left && !isSolid(x - dx, y, x + hitbox.width,y + hitbox.height)) {
            x -= dx;
            isFacingRight = false;
        }

    }

    private void updateFalling() {
        if (!isSolid(x, y, x + hitbox.width,y + hitbox.height + gravity)) y += gravity;
    }

    private boolean isSolid(int borderLeft, int borderTop, int borderRight, int borderBottom) {
        return ((borderLeft <= 0) || (borderTop <= 0) || (borderRight >= MAP_WIDTH) || (mapData[borderBottom][x] >= 200));
    }

    private void updateAnimationTick() {
        if (++animTick >= animSpeed) {
            animTick = 0;
            if (++animIdx >= 10)
                animIdx = 0;
        }
    }


    public void resetAnimationTick() {
        animTick = animIdx = 0;
    }


}
