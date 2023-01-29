package sprite;


import lombok.Data;
import util.ImagesLoader;
import util.ImagesPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public abstract class Sprite{
    private static final int X_STEP = 1;
    private static final int Y_STEP = 1;
    private static final int GRAVITY = 1;

    protected int x, y;
    protected int width, height;
    protected int dx, dy;
    protected int gravity;
    protected Rectangle hitbox;
    protected boolean up, right, down, left;

    private ImagesLoader imagesLoader;
    private String imageName;
    private BufferedImage image;

    private ImagesPlayer player;
    private boolean isLooping;

    public Sprite(int x, int y, int w, int h, ImagesLoader imsLd, String name) {
        this.x = x; this.y = y;
        width = w; height = h;
        
        imagesLoader = imsLd;
        setImage(name);
        hitbox = new Rectangle(x, y, w, h);
        dx = X_STEP; dy = Y_STEP;
        gravity = GRAVITY;

    }

    public void setImage(String name) {
        imageName = name;
        image = imagesLoader.getImage(imageName);
        if (image == null) System.out.println("No sprite image for " + imageName);
        else {

        }
        player = null;
        isLooping = false;
    }

    public void loopImage(int animationPeriod, double sequenceDuration) {
        if (imagesLoader.numImages(imageName) > 1) {
            player = null;
            player = new ImagesPlayer(imageName, animationPeriod, sequenceDuration, true, imagesLoader);
            isLooping = true;
        } else System.out.println(imageName + " is not a sequence of images");
    }

    protected void updateHitbox() {
        hitbox.x = x;
        hitbox.y = y;
    }

    public void render(Graphics g){
        if (image == null) g.fillRect(x, y, width, height);
        else {
            if (isLooping) image = player.getCurrentImage();
            g.drawImage(image, x, y, width, height, null);
        }
    }


}
