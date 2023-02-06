package sprite;

import util.SoundsLoader;
import util.ImagesLoader;


public class Mario extends Sprite {
    private static final int PLAYER_WIDTH = 48;
    private static final int PLAYER_HEIGHT = 48;
    private static final int JUMP_SPEED = 8;

    private int dx = 1, dy = 8;
    private boolean isUp, isRight, isDown, isLeft, isJump;
    private boolean isFacingRight = true;
    private SoundsLoader soundsLoader;
    public SoundsLoader getSoundManager() {
        return soundsLoader;
    }
    public Mario(int x, int y, ImagesLoader imagesLoader, SoundsLoader soundsLoader) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, imagesLoader, "idleRight");
        this.soundsLoader = soundsLoader;
    }

    public void updateSprite(){
        updateImage();
        updatePosition();
        updateJumpStatus();
        updateFalling();
        super.updateSprite();
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
        if (isUp && !isSolid(x, y - JUMP_SPEED, x + width,y + height)) {
            y -= JUMP_SPEED;
            if (isRight && !isSolid(x + dx, y - JUMP_SPEED, x + width,y + height)) {
                x += (dx / 2);
            }
            if (isLeft && !isSolid(x - dx, y - JUMP_SPEED, x + width,y + height)) {
                x -= (dx / 2);
            }
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
//        soundsLoader.playDieSound();
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

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public void stomp() {
            new Thread(() -> y -= GRAVITY).start();
    }

    public void jumping() {
        if(!isJump()) {
            setUp(true);
            soundsLoader.playJumpSound();
        }
    }
}
