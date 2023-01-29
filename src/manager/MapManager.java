package manager;

import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager {
    private static final String MAP1 = "map1.png";
    public static final int MAP_WIDTH = 10752;
    public static final int MAP_HEIGHT = 720;

    private BufferedImage mapSprite;

    public int[][] mapData;

    public MapManager() {
        mapSprite = ImagesLoader.loadImage(MAP1);

        mapData = new int[MAP_HEIGHT][MAP_WIDTH];

        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                if (i == 0 || j == 0 || i == MAP_HEIGHT - 1 || j == MAP_WIDTH - 1){
                    mapData[i][j] = 255;
                } else {
                    Color color = new Color(mapSprite.getRGB(j, i));
                    mapData[i][j] = color.getRed();
                }
            }
        }
    }

    public int[][] getMapData() {
        return mapData;
    }

    public void update() {}

    public void draw(Graphics g) {
        g.drawImage(mapSprite, 0, 0, MAP_WIDTH, MAP_HEIGHT,null);
    }
}
