package map;

import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager {
    public static final int MAP_WIDTH = 10752;
    public static final int MAP_HEIGHT = 720;

    private BufferedImage map;
    private ImagesLoader imagesLoader;

    public GameElement gameElement;

    public MapManager(ImagesLoader imagesLoader, String mapName) {
        this.imagesLoader = imagesLoader;
        setMap(mapName);
        resetGame(mapName);
    }

    private void setMap(String mapName) {
        map = imagesLoader.getImage(mapName);
        if (map == null) System.out.println("No sprite image for " + mapName);
    }
    private void resetGame(String mapName) {
        GameElement game = switch (mapName) {
            case "world1level1" -> new World1Level1(imagesLoader);
            default -> null;
        };
        gameElement = game;
    }

    public void drawSprite(Graphics g) {
        g.drawImage(map, 0, 0, MAP_WIDTH, MAP_HEIGHT,null);
        gameElement.drawSprite(g);
    }

}
