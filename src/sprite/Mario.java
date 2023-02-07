package sprite;

import map.MapManager;
import sprite.block.Block;
import sprite.block.ItemBrick;
import sprite.block.Pipe;
import sprite.block.RedBrick;
import sprite.enemy.Enemy;
import util.SoundsLoader;
import util.ImagesLoader;

import java.util.ArrayList;


public class Mario extends Sprite {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;
    private static final int MAX_UP_STEPS = 8;

    private int dx, dy, upCount, vertMoveMode;
    private boolean isUp, isRight, isDown, isLeft, isJump;
    private boolean isFacingRight = true;
    private ArrayList<Sprite> mapElements;
    private SoundsLoader soundsLoader;


    public Mario(int x, int y, ImagesLoader imagesLoader, SoundsLoader soundsLoader, MapManager mapManager) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "idleRight");
        this.soundsLoader = soundsLoader;
        this.mapElements = mapManager.getMapElements();

        dx = 1; dy = 8; upCount = 0;
    }

    public void updateSprite(){
        super.updateSprite();
        updateImage();
        updatePosition();
        updateJumpStatus();
        updateFalling();
        checkCollision();
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
        if (upCount == MAX_UP_STEPS) {
            isJump = false;
            upCount = 0;
        } else {
            upCount++;
        }
        System.out.println(upCount);
        isJump = !isSolid(x, y, x + width, y + height + GRAVITY);
    }

    private void checkCollision() {
        for (Sprite element: mapElements) {
            if (this.intersects(element)) {
                if (element instanceof Enemy enemy) enemyCollision(enemy);
                if (element instanceof Block block) blockCollision(block);
            }
        }
    }

    private void enemyCollision(Enemy enemy) {
        if (!enemy.isDie()) {
            if (isJump()) {
                enemy.setDie();
//                score += 500;
                y -= height;
            } else setDie();
        }
    }

    private void blockCollision(Block block) {
        if (y < block.y) {
            y = block.y - height;
            setJump(false);
        }

        if (y > block.y) {
            if (block instanceof RedBrick || block instanceof ItemBrick) {
                if (y < block.y + block.height) y = block.y + block.height;
                if (block instanceof ItemBrick) {
                    ((ItemBrick) block).shake();
//                    score += 500;
                }
            }

            if (block instanceof Pipe) {
                if (x < block.x) x = block.x - width;
                else x = block.x + block.width;
            }
        }
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
