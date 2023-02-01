package sprite;

import manager.SoundManager;
import util.ImagesLoader;

import java.util.ArrayList;


public class Player extends Sprite {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;
    private static final int JUMP_SPEED = 5;

    private int dx = 1, dy = 1, xVelocity, yVelocity;
    private boolean isUp, isRight, isDown, isLeft, isJump;
    private boolean isFacingRight = true;
    private SoundManager soundManager;

    ArrayList enemies;
    public Player(int x, int y, ImagesLoader imagesLoader, SoundManager soundManager, String mapName, ArrayList enemies) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "idleRight", mapName);
        this.soundManager = soundManager;
        this.enemies = enemies;
    }

    public void update(){
        updateImage();
        updatePosition();
        updateJumpStatus();
        super.update();
    }



    private void updateImage() {
        if (isFacingRight) setImage("idleRight");
        if (!isFacingRight) setImage("idleLeft");

        if (isJump && isFacingRight) setImage("jumpRight");
        if (isJump && !isFacingRight) setImage("jumpLeft");

        if (!isJump && isRight) setRunningImages("runningRight");
        if (!isJump && isLeft) setRunningImages("runningLeft");
    }

    private void setRunningImages(String name) {
        setImage(name);
        loopImage(20);
        if (name == "runningRight") isFacingRight = true;
        else isFacingRight = false;
    }

    private void updatePosition() {
        if (isUp && !isSolid(x, y - JUMP_SPEED, x + width,y + height)) {
            y -= JUMP_SPEED;
        }
        if (isUp && isRight && !isSolid(x + dx, y - JUMP_SPEED, x + width,y + height)) {
            x += dx; y -= JUMP_SPEED;
        }
        if (isUp && isLeft && !isSolid(x - dx, y - JUMP_SPEED, x + width,y + height)) {
            x -= dx; y -= JUMP_SPEED;
        }
        if (isRight && !isSolid(x, y, x + width + dx,y + height)) {
            x += dx; isFacingRight = true;
        }
        if (isLeft && !isSolid(x - dx, y, x + width,y + height)) {
            x -= dx; isFacingRight = false;
        }
    }

    private void updateJumpStatus() {
        if (isSolid(x, y, x + width,y + height + GRAVITY)) isJump = false;
        else {
            isJump = true;
        }
    }

    public void setDie() {
        setImage("die");
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public boolean isJump() {
        return isJump;
    }


}
