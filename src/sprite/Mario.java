package sprite;

import util.SoundsLoader;
import util.ImagesLoader;

import java.util.Objects;


public class Mario extends Sprite {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    private int dx = 1, dy = 8, jumpHeight = 20;
    private boolean isUp, isRight, isDown, isLeft, isJump;
    private boolean isFacingRight = true;
    private SoundsLoader soundsLoader;

    public Mario(int x, int y, ImagesLoader imagesLoader, SoundsLoader soundsLoader) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "idleRight");
        this.soundsLoader = soundsLoader;
    }

    public void updateSprite(){
        updateImage();
        updatePosition();
        updateJumpStatus();
        checkIfFalling();
        super.updateSprite();
    }

    private void updateImage() {
        if (isFacingRight)  setImage("idleRight");
        if (!isFacingRight) setImage("idleLeft");

        if (isJump && isFacingRight)  setImage("jumpRight");
        if (isJump && !isFacingRight) setImage("jumpLeft");

        if (!isJump && isRight) {
            setImage("runningRight");
            loopImage(120);
            isFacingRight = true;
        }
        if (!isJump && isLeft) {
            setImage("runningLeft");
            loopImage(120);
            isFacingRight = false;
        }
    }

    private void updatePosition() {
        if (isUp && !isSolid(x, y - dy, x + width,y + height)) {
            y -= dy;
            if (isRight && !isSolid(x + dx, y - dy, x + width,y + height)) {
                x += (dx / 2);
            }
            if (isLeft && !isSolid(x - dx, y - dy, x + width,y + height)) {
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

    // ------------------------ Getter / Setter ------------------------

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

    public void jumping() {
        if(!isJump()) {
            setUp(true);
            soundsLoader.playJumpSound();
        }
    }
}
