package manager;

import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager {
    private static final String MAP1 = "map1.png";
    public static final int MAP_WIDTH = 10752;
    public static final int MAP_HEIGHT = 720;

    private BufferedImage mapSprite;


    public MapManager() {
        mapSprite = ImagesLoader.loadImage(MAP1);
    }

    public void draw(Graphics g) {
        g.drawImage(mapSprite, 0, 0, MAP_WIDTH, MAP_HEIGHT,null);
    }
}
