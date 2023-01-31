package sprite;

import util.ImagesLoader;

import java.awt.*;

public class Enemy extends Sprite{
    private int dx = 1;


    public Enemy(int x, int y, int width, int height, ImagesLoader imagesLoader, String imageName, String mapName) {
        super(x, y, width, height, imagesLoader, imageName, mapName);

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
        super.render(g);
    }
}
