package sprite.block;

import util.ImagesLoader;

public class Pipe extends Block {
    private static final int WIDTH = 96;

    public static final int SMALL_SIZE = 96;
    public static final int MEDIUM_SIZE = 128;
    public static final int LARGE_SIZE = 170;


    public Pipe(int x, int y, int size, ImagesLoader imagesLoader) {
        super(x, y, WIDTH, size, imagesLoader, "pipe");
    }
}
