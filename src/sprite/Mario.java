package sprite;

import map.Map;
import sprite.block.Block;
import sprite.block.ItemBrick;
import sprite.block.Pipe;
import sprite.block.RedBrick;
import sprite.enemy.Enemy;
import util.ClipsLoader;
import util.ImagesLoader;

import java.util.ArrayList;


public class Mario extends Sprite {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;
    private static final int MAX_UP_STEPS = 80;

    private final int dx, dy;
    private int upCount, score;
    private boolean isUp, isRight, isLeft, isJump;
    private boolean maxUp, isFacingRight, isDie;
    private final ArrayList<Sprite> elements;
    private final ClipsLoader clipsLoader;


    public Mario(int x, int y, ImagesLoader imagesLoader, ClipsLoader clipsLoader, Map map) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "idleRight");
        this.clipsLoader = clipsLoader;
        this.elements = map.getElements();

        dx = 1; dy = 8; upCount = 0; score = 0;
        isFacingRight = true; isDie = false;
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

        if (isDie) setImage("die");
    }

    private void updatePosition() {
        if (!isDie) {
            if (isUp && !maxUp) {
                clipsLoader.play("jump", false);
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
    }

    private void updateRising() {
        if (isUp){
            if (upCount == MAX_UP_STEPS) {
                maxUp = true;
                upCount = 0;
            } else {
                isJump = true;
                upCount++;
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
        for (Sprite element: elements) {
            if (this.intersects(element)) {
                if (element instanceof Enemy enemy) enemyCollision(enemy);
                if (element instanceof Block block) blockCollision(block);
            }
        }
    }

    private void enemyCollision(Enemy enemy) {
        if (!enemy.isDie()) {
            boolean canKillEnemy = y < enemy.y;
            if (canKillEnemy) {
                clipsLoader.play("stomp", false);
                enemy.die();
                y -= height;
                score += 100;
            } else die();
        }
    }

    private void die() {
        if (!isDie) {
            isDie = true;
            clipsLoader.stop("background");
            clipsLoader.play("die", false);
        }
    }

    private void blockCollision(Block block) {
        boolean isUnderBlock = y > block.y;

        if (!isUnderBlock) {
            y = block.y - height;
            resetJump();
        }

        if (isUnderBlock) {
            if (block instanceof RedBrick || block instanceof ItemBrick) {
                if (y < block.y + block.height) {
                    y = block.y + block.height;
                    maxUp = true;
                }
                if (block instanceof ItemBrick && !((ItemBrick) block).isHit()) {
                    ((ItemBrick) block).shake();
                    clipsLoader.play("coin", false);
                    score += 200;
                }
            }

            if (block instanceof Pipe) {
                if (x < block.x) x = block.x - width;
                else x = block.x + block.width;
            }
        }
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public int getScore() {
        return score;
    }

    public boolean isDie() {
        return isDie;
    }
}
