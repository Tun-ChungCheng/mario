package sprite.enemy;

import sprite.Sprite;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;

public abstract class Enemy extends Sprite {
    private int dx = 1;
    private boolean isDie = false;

    public Enemy(int x, int y, int width, int height, ImagesLoader imagesLoader, String imageName) {
        super(x, y, width, height, imagesLoader, imageName);

        setImage(imageName);
        loopImage(20);
    }

    public void updateSprite() {
        super.updateSprite();
        updatePosition();
        updateFalling();
    }

    private void updatePosition() {
        x -= dx;
    }

    private void updateFalling() {
        if (y + height + GRAVITY < FLOOR_HEIGHT) y += GRAVITY;
    }

    public void drawSprite(Graphics g) {
        if (!isDie) super.drawSprite(g);
    }

    public void changeDirection () {
        dx *= -1;
    }

    public void die() {
        dx = 0; setImage("goombaDie");
        new Timer(50, (e) ->  isDie = true).start();
    }

    public boolean isDie() {
        return isDie;
    }
}
