package entity;

import util.Load;

import java.awt.*;
import java.awt.image.BufferedImage;

import static constant.PlayerConstants.*;


public class Player extends Entity{
    private int animationTick, animationIdx, animationSpeed = 120;
    private int playAction = RUN_RIGHT;
    private int direction = DIR_RIGHT;
    private BufferedImage[][] animations;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage image = Load.GetSpriteAtlas(Load.PLAYER_ATLAS);
        animations = new BufferedImage[9][3];

        animations[DIE][0] = image.getSubimage(0,     15, 15, 15);

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
        g.drawImage(animations[playAction][animationIdx], x, y, 150, 150, null);
    };

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPlayAction() {
        return playAction;
    }

    public void setPlayAction(int playAction) {
        this.playAction = playAction;
    }
}
