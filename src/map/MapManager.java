package map;

import sprite.block.Block;
import sprite.enemy.Enemy;
import sprite.Sprite;
import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapManager {
    public static final int MAP_WIDTH = 10752;
    public static final int MAP_HEIGHT = 720;

    private BufferedImage map;
    private final ImagesLoader imagesLoader;
    private GameElement gameElement;
    private ArrayList<Sprite> mapElements;
    private final String mapName;


    public MapManager(ImagesLoader imagesLoader, int world, int level) {
        this.imagesLoader = imagesLoader;
        mapName = "world" + world + "level" + level;
        setMap();
        resetGame();
    }

    private void setMap() {
        map = imagesLoader.getImage(mapName);
        if (map == null) System.out.println("No sprite image for " + mapName);
    }

    private void resetGame() {
        GameElement game = switch (mapName) {
            case "world1level1" -> new World1Level1(imagesLoader);
            default -> null;
        };
        gameElement = game;
        assert game != null;
        mapElements = game.getElements();
    }

    public void drawSprite(Graphics g) {
        g.drawImage(map, 0, 0, MAP_WIDTH, MAP_HEIGHT,null);
        gameElement.drawSprite(g);
    }

    public void updateSprite() {
        mapElements.forEach(Sprite::updateSprite);
        checkCollision();
    }

    private void checkCollision() {
        for (Sprite element: mapElements)
            if (element instanceof Enemy) enemyCollision((Enemy) element);
    }

    private void enemyCollision(Enemy enemy) {
        for (Sprite element: mapElements) {
            if (element instanceof Block block && enemy.intersects(element)) {
                if (enemy.x < block.x) enemy.x = Math.min(enemy.x, block.x - enemy.width);
                else enemy.x = Math.max(enemy.x, block.x + block.width);
                enemy.changeDirection();
            }
        }
    }

    public ArrayList<Sprite> getMapElements() {
        return mapElements;
    }
}
