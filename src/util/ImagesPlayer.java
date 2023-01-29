package util;

import java.awt.image.BufferedImage;

public class ImagesPlayer {

    private String imageName;
    private int animationPeriod;
    private long animationTotalTime;
    private double sequenceDuration;
    private boolean isRepeating;
    private ImagesLoader imagesLoader;
    private int numImages;
    private int imagePosition;
    private boolean ticksIgnored;

    public ImagesPlayer(String imageName, int animationPeriod, double sequenceDuration, boolean isRepeating, ImagesLoader imagesLoader) {
        this.imageName = imageName;
        this.animationPeriod = animationPeriod;
        this.sequenceDuration = sequenceDuration;
        this.isRepeating = isRepeating;
        this.imagesLoader = imagesLoader;

        animationTotalTime = 0L;

        if (sequenceDuration < 0.5) {
            System.out.println("Warning: minimum sequence duration is 0.5 sec.");
            sequenceDuration = 0.5;
        }

        if (!imagesLoader.isLoaded(imageName)) {
            System.out.println(imageName + " is not known by the ImagesLoader");
            numImages = 0;
            imagePosition = -1;
            ticksIgnored = true;
        } else {
            numImages = imagesLoader.numImages(imageName);
            imagePosition = 0;
            ticksIgnored = false;
        }
    }

    public BufferedImage getCurrentImage() {
        if (numImages != 0) return imagesLoader.getImage(imageName, imagePosition);
        else return null;
    }
}
