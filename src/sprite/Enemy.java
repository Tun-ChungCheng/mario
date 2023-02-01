package sprite;

import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;

public class Enemy extends Sprite{
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private int dx = 1;
    private boolean isDie = false;


    public Enemy(int x, int y, ImagesLoader imagesLoader, String imageName, String mapName) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, imageName, mapName);

        setImage(imageName);
        loopImage(20);
    }

    public void update() {
        super.update();
        updatePosition();
    }

    private void updatePosition() {
        if (isSolid(x - dx, y, width + dx, height)) dx *= -1;
        x -= dx;
    }

    public void render(Graphics g) {
        if (!isDie) super.render(g);
    }

    public void setDie() {
        dx = 0;
        setImage("mushroomDie");
        new Timer(1000, (e) -> isDie = true).start();
    }
}
