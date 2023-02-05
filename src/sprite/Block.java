package sprite;

import util.ImagesLoader;

public abstract class Block extends Sprite{
    public Block(int x, int y, int width, int height, ImagesLoader imagesLoader, String imageName, String mapName) {
        super(x, y, width, height, imagesLoader, imageName, mapName);
    }
}
