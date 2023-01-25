package entity;

import main.Game;
import util.LoadImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static constant.PlayerConst.*;


public class Player extends Entity{
    private int animationTick, animationIdx, animationSpeed = 90;
    private int playAction = IDLE_RIGHT;
    private int direction = DIR_RIGHT;

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
        updatePosition();
        updateHitbox();
    }

    private void updatePosition() {

        if (isHit()) {

        } else {
            switch (playAction) {
                case JUMP_LEFT, JUMP_RIGHT -> y -= DELTA_Y;
                case RUN_RIGHT -> x += DELTA_X;
                case DIE -> y += DELTA_Y;
                case RUN_LEFT -> x -= DELTA_X;
            }
        }

        if (mapData[(int)y][(int)x] >= 200 ) System.out.println("hit");
    }

    private boolean isHit() {
        return (hitbox.x - (2 * DELTA_X) <= 0);


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
        drawHitbox(g);
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
}
