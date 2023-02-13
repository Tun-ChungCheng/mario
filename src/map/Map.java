package map;

import sprite.block.Block;
import sprite.block.ItemBrick;
import sprite.block.Pipe;
import sprite.block.RedBrick;
import sprite.enemy.Enemy;
import sprite.Sprite;
import sprite.enemy.Goomba;
import util.ImagesLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static sprite.block.Pipe.*;

public class Map {
    public static final int MAP_WIDTH = 10752;
    public static final int MAP_HEIGHT = 720;

    private BufferedImage map;
    private final ImagesLoader imagesLoader;
    private final ArrayList<Sprite> elements;


    public Map(ImagesLoader imagesLoader) {
        this.imagesLoader = imagesLoader;

        setMap("map");

        elements = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Block> blocks  = new ArrayList<>();

        for (int i = 500; i < 9000; i += 100) {
            int randomNumber = (int)(Math.random() * 9);
            switch (randomNumber) {
                case 1 ->  blocks.add(new RedBrick(i, 300, imagesLoader));
                case 2 ->  blocks.add(new ItemBrick(i, 300, imagesLoader));
                case 3 ->  blocks.add(new RedBrick(i, 200, imagesLoader));
                case 4 ->  blocks.add(new ItemBrick(i, 200, imagesLoader));
                case 5 ->  blocks.add(new Pipe(i, 526, SMALL_SIZE, imagesLoader));
                case 6 ->  blocks.add(new Pipe(i, 495, MEDIUM_SIZE, imagesLoader));
                case 7 ->  blocks.add(new Pipe(i, 453, LARGE_SIZE, imagesLoader));
                case 8 -> enemies.add(new Goomba(i, 500, imagesLoader));
            }
        }

        elements.addAll(enemies);
        elements.addAll(blocks);
    }

    private void setMap(String name) {
        map = imagesLoader.getImage(name);
        if (map == null) System.out.println("No sprite image for " + name);
    }

    public void drawSprite(Graphics g) {
        g.drawImage(map, 0, 0, MAP_WIDTH, MAP_HEIGHT,null);
        for (Sprite element:elements)
            element.drawSprite(g);
    }

    public void updateSprite() {
        elements.forEach(Sprite::updateSprite);
        checkCollision();
    }

    private void checkCollision() {
        for (Sprite element: elements)
            if (element instanceof Enemy) enemyCollision((Enemy) element);
    }

    private void enemyCollision(Enemy enemy) {
        for (Sprite element: elements) {
            if (element instanceof Block block && enemy.intersects(element)) {
                if (enemy.x < block.x) enemy.x = Math.min(enemy.x, block.x - enemy.width);
                else enemy.x = Math.max(enemy.x, block.x + block.width);
                enemy.changeDirection();
            }
        }
    }

    public ArrayList<Sprite> getElements() {
        return elements;
    }
}
