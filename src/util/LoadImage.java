package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadImage {
    public static final String PLAYER_ATLAS = "image/mario.png";
    public static final String LEVEL_ATLAS  = "image/map-1-1.png";

    public static BufferedImage GetSpriteAtlas(String pathname) {
        BufferedImage img;

        try {
            img = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
}
