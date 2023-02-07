package sprite;

import util.ImagesLoader;

import javax.swing.*;

public class ItemBrick extends Block{
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    public ItemBrick(int x, int y, ImagesLoader imagesLoader) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "itemBrick");
    }

    public void shake() {
        new Thread(() -> {
            y -= 10;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            y += 10;
        }).start();
    }
}
