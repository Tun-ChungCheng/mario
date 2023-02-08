package sprite.enemy;

import util.ImagesLoader;

public class Goomba extends Enemy {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;
    public Goomba(int x, int y, ImagesLoader imagesLoader) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "goomba");
    }
}
