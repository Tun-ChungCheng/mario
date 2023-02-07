package sprite;

import main.GamePanel;
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
    private static final int MAX_UP_STEPS = 50;

    private int dx, dy, upCount, score;
    private boolean isUp, isRight, isDown, isLeft, isJump,
                    isFacingRight, maxUp;
    private ArrayList<Sprite> mapElements;
    private SoundsLoader      soundsLoader;
    private GamePanel         gamePanel;


    public Mario(int x, int y, ImagesLoader imagesLoader,
                 SoundsLoader soundsLoader, MapManager mapManager, GamePanel gamePanel) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "idleRight");
        this.soundsLoader = soundsLoader;
        this.mapElements = mapManager.getMapElements();
        this.gamePanel = gamePanel;

        dx = 1; dy = 8; upCount = 0; score = 0;
        isFacingRight = true;
    }

    public void updateSprite(){
        super.updateSprite();
        updateImage();
        updatePosition();
        updateRising();
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
            loopImage(30);
            isFacingRight = true;
        }
        if (!isJump && isLeft) {
            setImage("runningLeft");
            loopImage(30);
            isFacingRight = false;
        }
    }

    private void updatePosition() {
        if (isUp && !maxUp) {
            y -= dy;
            if (isRight) x += (dx / 2);
            if (isLeft) x -= (dx / 2);
        }
        if (isRight) {
            isFacingRight = true;
            x += dx;
        }
        if (isLeft) {
            isFacingRight = false;
            x -= dx;
        }
    }

    private void updateRising() {
        if (isUp){
            if (upCount == MAX_UP_STEPS) {
                maxUp = true;
                upCount = 0;
            } else {
                upCount++;
                isJump = true;
            }
        }
    }

    private void updateFalling() {
        if (y + height + GRAVITY < FLOOR_HEIGHT) y += GRAVITY;
        else resetJump();
    }

    private void resetJump() {
        upCount = 0;
        maxUp = false;
        isJump = false;
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
            if (isJump) {
                enemy.die();
                y -= height;
                score += 100;
            } else die();
        }
    }

    private void die() {
        setImage("die");
        gamePanel.setGameOver(true);
//        soundsLoader.playDieSound();
    }

    private void blockCollision(Block block) {
        if (y < block.y) {
            y = block.y - height;
            resetJump();
        }

        if (y > block.y) {
            if (block instanceof RedBrick || block instanceof ItemBrick) {
                if (y < block.y + block.height) y = block.y + block.height;
                if (block instanceof ItemBrick && !((ItemBrick) block).isHit()) {
                    ((ItemBrick) block).shake();
                    score += 200;
                }
            }

            if (block instanceof Pipe) {
                if (x < block.x) x = block.x - width;
                else x = block.x + block.width;
            }
        }
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

    public int getScore() {
        return score;
    }
}
