package sprite.block;

import sprite.Sprite;
import util.ImagesLoader;

public abstract class Block extends Sprite {
    public Block(int x, int y, int width, int height, ImagesLoader imagesLoader, String imageName) {
        super(x, y, width, height, imagesLoader, imageName);
    }
}
