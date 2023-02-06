package sprite;


import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static map.MapManager.MAP_WIDTH;


public abstract class Sprite extends Rectangle{
    protected static final int GRAVITY = 3;

    private static final int FLOOR_HEIGHT = 625;

    private boolean       isLooping, collision = true;
    private int           animationTick, 
                          animationIndex, 
                          animationSpeed;
    private String        imageName;
    private ImagesLoader  imagesLoader;
    private BufferedImage image;


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

    protected void loopImage(int animationSpeed) {
        if (imagesLoader.numImages(imageName) > 1) {
            this.animationSpeed = animationSpeed;
            isLooping = true;
        } else System.out.println(imageName + " is not a sequence of images");
    }

    private void updateTick() {
        if (++animationTick >= animationSpeed) {
            animationTick = 0;
            if (++animationIndex >= imagesLoader.numImages(imageName))
                animationIndex = 0;
        }
    }

    public void updateSprite() {
        if (isLooping) updateTick();
    }

    protected void updateFalling() {
        if (!isSolid(x, y, x + width,y + height + GRAVITY)) y += GRAVITY;
    }

    protected boolean isSolid(int left, int top, int right, int bottom) {
        return ((left <= 0) || (top <= 0) || (right >= MAP_WIDTH) || (bottom >= FLOOR_HEIGHT));
    }

    public void drawSprite(Graphics g){
        if (image == null) g.fillRect(x, y, width, height);
        else {
            if (isLooping) image = imagesLoader.getImage(imageName, animationIndex);
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public void setCollisionTimer() {
        collision = false;
        new Timer(200, (e) -> collision = true).start();
    }

    public boolean isCollision() {
        return collision;
    }
}
