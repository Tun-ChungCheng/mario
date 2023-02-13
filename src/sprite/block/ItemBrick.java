package sprite.block;

import util.ImagesLoader;


public class ItemBrick extends Block {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 48;

    private boolean hit = false;
    private int maxCount;


    public ItemBrick(int x, int y, ImagesLoader imagesLoader) {
        super(x, y, WIDTH, HEIGHT, imagesLoader, "itemBrick");
        maxCount = (int)(Math.random() * 5) + 1;
    }

    public void shake() {
        if (!hit) {
            new Thread(this::run).start();
        }
    }

    private void run() {
        maxCount--;
        if (maxCount == 0) {
            hit = true;
            setImage("emptyBrick");
        }

        y -= 5;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        y += 5;
    }

    public boolean isHit() {
        return hit;
    }

}
