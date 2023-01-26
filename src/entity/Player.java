package entity;

import main.Game;
import util.LoadImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static constant.GameConst.GAME_WIDTH;
import static constant.PlayerConst.*;


public class Player extends Entity{
    private int animationTick, animationIdx, animationSpeed = 20;
    private int playAction = IDLE_RIGHT;
    private int direction = DIR_RIGHT;
    private boolean up, right, down, left;
    private int[][] mapData;
    private BufferedImage[][] animations;


    Game game;
    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }

    public void loadMapData(int[][] mapData) {
        this.mapData = mapData;
    }


    private void loadAnimations() {
        BufferedImage image = LoadImage.GetSpriteAtlas(LoadImage.PLAYER_ATLAS);
        animations = new BufferedImage[9][3];

        animations[DIE][0] = image.getSubimage(0, 15, 15, 15);

        animations[JUMP_LEFT][0] = image.getSubimage(2 * 15, 0, 15, 15);
        animations[TURN_LEFT][0] = image.getSubimage(4 * 15, 0, 15, 15);
        animations[IDLE_LEFT][0] = image.getSubimage(12 * 15, 0, 15, 15);

        for (int i = 0, j = 0; i < 3; i++, j += 2) {
            animations[RUN_LEFT][i]  = image.getSubimage((6 + j) * 15, 0, 15, 15);
            animations[RUN_RIGHT][i] = image.getSubimage((16 + j) * 15, 0, 15, 15);
        }

        animations[IDLE_RIGHT][0] = image.getSubimage(14 * 15, 0, 15, 15);
        animations[TURN_RIGHT][0] = image.getSubimage(22 * 15, 0, 15, 15);
        animations[JUMP_RIGHT][0] = image.getSubimage(24 * 15, 0, 15, 15);
    }

    public void update(){
        updateAnimationTick();
        updateAnimation();
        updatePosition();
        updateHitbox();
    }

    private void updateAnimation() {
        if (direction == DIR_LEFT) playAction = IDLE_LEFT;
        if (direction == DIR_RIGHT) playAction = IDLE_RIGHT;

        if (up && direction == DIR_LEFT) playAction = JUMP_LEFT;
        if (up && direction == DIR_RIGHT) playAction = JUMP_RIGHT;
        if (right) playAction = RUN_RIGHT;
        if (left) playAction = RUN_LEFT;

//        resetAnimationTick();

    }

    private void updatePosition() {
        if (up && !isSolid(x, y - JUMP_STRENGTH, x + hitbox.width,y + hitbox.height)) {
            y -= JUMP_STRENGTH;
        }
        if (up && right && !isSolid(x + DELTA_X, y - JUMP_STRENGTH, x + hitbox.width,y + hitbox.height)) {
            x += DELTA_X; y -= JUMP_STRENGTH;
        }
        if (up && left && !isSolid(x - DELTA_X, y - JUMP_STRENGTH, x + hitbox.width,y + hitbox.height)) {
            x -= DELTA_X; y -= JUMP_STRENGTH;
        }
        if (right && !isSolid(x, y, x + hitbox.width + DELTA_X,y + hitbox.height)) {
            x += DELTA_X;
            direction = DIR_RIGHT;
        }
        if (left && !isSolid(x - DELTA_X, y, x + hitbox.width,y + hitbox.height)) {
            x -= DELTA_X;
            direction = DIR_LEFT;
        }

        fall();
    }

    private void fall() {
        if (!isSolid(x, y, x + hitbox.width,y + hitbox.height + GRAVITY)) y += GRAVITY;
    }

    private boolean isSolid(int borderLeft, int borderTop, int borderRight, int borderBottom) {
        return ((borderLeft <= 0) || (borderTop <= 0) || (borderRight >= GAME_WIDTH) || (mapData[borderBottom][x] >= 200));
    }

    private boolean isHit() {
        return false;
    }

    private void updateAnimationTick() {
        if (++animationTick >= animationSpeed) {
            animationTick = 0;
            if (++animationIdx >= GetSpriteActionAmount(playAction))
                animationIdx = 0;
        }
    }

    public void resetAnimationTick() {
        animationTick = animationIdx = 0;
    }

    public void render(Graphics g){
        g.drawImage(animations[playAction][animationIdx], (int)x, (int)y, width, height, null);
    };

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPlayAction(int playAction) {
        this.playAction = playAction;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
