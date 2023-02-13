package sprite;


import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Sprite extends Rectangle{
    protected static final int GRAVITY = 3;

    protected static final int FLOOR_HEIGHT = 625;

    private boolean isLooping;
    private int imageTick;
    private int imageSpeed;
    private int imageIndex;
    private String imageName;
    private BufferedImage image;
    private final ImagesLoader imagesLoader;


    public Sprite(int x, int y, int width, int height,
                  ImagesLoader imagesLoader, String imageName) {
        super(x, y, width, height);
        this.imagesLoader = imagesLoader;

        setImage(imageName);
    }

    public void setImage(String name) {
        imageName = name;
        image = imagesLoader.getImage(name);
        if (image == null) System.out.println("No sprite image for " + imageName);
        isLooping = false;
    }

    protected void loopImage(int imageSpeed) {
        if (imagesLoader.numImages(imageName) > 1) {
            this.imageSpeed = imageSpeed;
            isLooping = true;
        } else System.out.println(imageName + " is not a sequence of images");
    }

    private void updateTick() {
        if (++imageTick >= imageSpeed) {
            imageTick = 0;
            if (++imageIndex >= imagesLoader.numImages(imageName))
                imageIndex = 0;
        }
    }

    public void updateSprite() {
        if (isLooping) updateTick();
    }

    public void drawSprite(Graphics g){
        if (image == null) g.fillRect(x, y, width, height);
        else {
            if (isLooping) image = imagesLoader.getImage(imageName, imageIndex);
            g.drawImage(image, x, y, width, height, null);
        }
    }
}
