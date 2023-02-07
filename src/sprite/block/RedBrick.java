package sprite.block;

import util.ImagesLoader;

public class RedBrick extends Block {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    public RedBrick(int x, int y, ImagesLoader imagesLoader) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "redBrick");
    }
}
