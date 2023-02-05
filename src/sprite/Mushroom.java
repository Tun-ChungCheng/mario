package sprite;

import util.ImagesLoader;

public class Mushroom extends Enemy{
    public Mushroom(int x, int y, ImagesLoader imagesLoader, String mapName) {
        super(x, y, imagesLoader, "mushroom", mapName);
    }
}
