package sprite;

import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;

public abstract class Enemy extends Sprite{
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private int dx = 1;
    private boolean isDie = false;


    public Enemy(int x, int y, ImagesLoader imagesLoader, String imageName) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, imageName);

        setImage(imageName);
        loopImage(20);
    }

    public void updateSprite() {
        super.updateSprite();
        updatePosition();
        updateFalling();
    }

    private void updatePosition() {
        if (isSolid(x - dx, y, x + width + dx, y + height)) changeDirection();
        x -= dx;
    }

    public void drawSprite(Graphics g) {
        if (!isDie) super.drawSprite(g);
    }

    public void setDie() {
        dx = 0;
        setImage("mushroomDie");
        new Timer(10, (e) -> isDie = true).start();
    }

    public boolean isDie() {
        return isDie;
    }

    public void changeDirection () {
        dx *= -1;
    }
}
