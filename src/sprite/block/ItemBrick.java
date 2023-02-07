package sprite.block;

import util.ImagesLoader;

public class ItemBrick extends Block {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    private boolean hit = false;

    public ItemBrick(int x, int y, ImagesLoader imagesLoader) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "itemBrick");
    }

    public void shake() {
        if (!hit) {
            new Thread(this::run).start();
        }
    }

    private void run() {
        y -= 5;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        y += 5;
        hit = true;
        setImage("emptyBrick");
    }
}
