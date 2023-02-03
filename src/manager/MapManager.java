package manager;

import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager {
    public static final int MAP_WIDTH = 10752;
    public static final int MAP_HEIGHT = 720;

    private BufferedImage map;
    private ImagesLoader imagesLoader;


    public MapManager(ImagesLoader imagesLoader, String mapName) {
        this.imagesLoader = imagesLoader;
        setMap(mapName);


    }

    private void setMap(String mapName) {
        map = imagesLoader.getImage(mapName);
        if (map == null) System.out.println("No sprite image for " + mapName);
    }

    public void draw(Graphics g) {
        g.drawImage(map, 0, 0, MAP_WIDTH, MAP_HEIGHT,null);
    }
}
