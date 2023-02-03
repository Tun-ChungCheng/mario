package sprite;

import manager.SoundManager;
import util.ImagesLoader;


public class Player extends Sprite {
    private static final int PLAYER_WIDTH = 48;
    private static final int PLAYER_HEIGHT = 48;
    private static final int JUMP_SPEED = 5;

    private int dx, dy = 1, previousX, currentX;
    private boolean isUp, isRight, isDown, isLeft, isJump;
    private boolean isFacingRight = true;
    private SoundManager soundManager;


    public Player(int x, int y, ImagesLoader imagesLoader,
                  SoundManager soundManager, String mapName) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, imagesLoader, "idleRight", mapName);
        this.soundManager = soundManager;
        previousX = currentX = x;
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
        isFacingRight = name == "runningRight";
    }

    private void updatePosition() {
        dx = Math.min(3, Math.abs(currentX - previousX + 1));

        System.out.println(dx);
        if (isUp && !isSolid(x, y - JUMP_SPEED, x + width,y + height)) {
            y -= JUMP_SPEED;
        }
        if (isUp && isRight && !isSolid(x + dx, y - JUMP_SPEED, x + width,y + height)) {
            x += (dx / 2);
            y -= JUMP_SPEED;
        }
        if (isUp && isLeft && !isSolid(x - dx, y - JUMP_SPEED, x + width,y + height)) {
            x -= (dx / 2);
            y -= JUMP_SPEED;
        }
        if (isRight && !isSolid(x, y, x + width + dx,y + height)) {
            isFacingRight = true;
            x += dx;
        }
        if (isLeft && !isSolid(x - dx, y, x + width,y + height)) {
            isFacingRight = false;
            x -= dx;
        }
    }

    private void updateJumpStatus() {
        isJump = !isSolid(x, y, x + width, y + height + GRAVITY);
    }

    public void setDie() {
        setImage("die");
//        soundManager.playSound("die");
    }

    public void playJumpSound() {
        soundManager.playSound("jump");
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

    public void setPreviousX(int previousX) {
        this.previousX = previousX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }
}
