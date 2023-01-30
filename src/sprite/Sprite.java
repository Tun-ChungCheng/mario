package sprite;


import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static manager.MapManager.MAP_WIDTH;


public abstract class Sprite{
    protected static final int X_STEP = 1;
    protected static final int Y_STEP = 1;
    protected static final int GRAVITY = 3;

    protected int x, y;
    protected int width, height;
    protected int dx = 1, dy = 1;
    

    private ImagesLoader imagesLoader;
    private String imageName;
    private BufferedImage image;
    private boolean isLooping;
    private int animationTick, animationIndex, animationSpeed;
    private int[][] mapRedPixelValue;



    public Sprite(int x, int y, int width, int height,
                  ImagesLoader imagesLoader, String imageName, String mapName) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.imagesLoader = imagesLoader;

        setImage(imageName);
        loadMapRedPixelValue(mapName);
    }

    public void setImage(String name) {
        imageName = name;
        image = imagesLoader.getImage(imageName);
        if (image == null) System.out.println("No sprite image for " + imageName);
        isLooping = false;
    }

    private void loadMapRedPixelValue(String mapName) {
        this.mapRedPixelValue = imagesLoader.getMapRedPixelValue(mapName);
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

    public void update() {
        if (isLooping) updateTick();
        updateFalling();
    }

    private void updateFalling() {
        if (!isSolid(x, y, x + width,y + height + GRAVITY)) y += GRAVITY;
    }

    protected boolean isSolid(int left, int top, int right, int bottom) {
        return ((left <= 0) || (top <= 0) || (right >= MAP_WIDTH) || (mapRedPixelValue[bottom][x] >= 200));
    }

    public void render(Graphics g){
        if (image == null) g.fillRect(x, y, width, height);
        else {
            if (isLooping) image = imagesLoader.getImage(imageName, animationIndex);
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
