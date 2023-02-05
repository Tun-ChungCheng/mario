package sprite;

import util.ImagesLoader;

public class ItemBrick extends Block{
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    public ItemBrick(int x, int y, ImagesLoader imagesLoader, String mapName) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "itemBrick", mapName);
    }
}
