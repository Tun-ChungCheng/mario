package map;

import sprite.*;
import util.ImagesLoader;

import java.util.ArrayList;

import static sprite.Pipe.*;

public class World1Level1 extends GameElement {
    public World1Level1(ImagesLoader imagesLoader) {
        elements = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Block> blocks  = new ArrayList<>();

        blocks.add(new ItemBrick(800, 380, imagesLoader, "world1level1"));

        blocks.add(new RedBrick(1000, 380, imagesLoader, "world1level1"));
        blocks.add(new ItemBrick(1048, 380, imagesLoader, "world1level1"));
        blocks.add(new RedBrick(1096, 380, imagesLoader, "world1level1"));
        blocks.add(new ItemBrick(1144, 380, imagesLoader, "world1level1"));
        blocks.add(new RedBrick(1192, 380, imagesLoader, "world1level1"));

        blocks.add(new ItemBrick(1096, 180, imagesLoader, "world1level1"));
        enemies.add(new Mushroom(1096, 480, imagesLoader, "world1level1"));

        blocks.add(new Pipe(1384, 526, SMALL_SIZE, imagesLoader, "world1level1"));

        blocks.add(new Pipe(1820, 495, MEDIUM_SIZE, imagesLoader, "world1level1"));
        enemies.add(new Mushroom(1916, 480, imagesLoader, "world1level1"));

        blocks.add(new Pipe(2210, 453, LARGE_SIZE, imagesLoader, "world1level1"));
        enemies.add(new Mushroom(2500, 480, imagesLoader, "world1level1"));
        enemies.add(new Mushroom(2548, 480, imagesLoader, "world1level1"));

        blocks.add(new Pipe(2745, 453, LARGE_SIZE, imagesLoader, "world1level1"));

        blocks.add(new RedBrick(3700, 380, imagesLoader, "world1level1"));
        blocks.add(new ItemBrick(3748, 380, imagesLoader, "world1level1"));
        blocks.add(new RedBrick(3796, 380, imagesLoader, "world1level1"));

        for (int i = 0; i < 9; i++)
            blocks.add(new RedBrick(3844 + 48 * i, 200, imagesLoader, "world1level1"));

        enemies.add(new Mushroom(3844, 168, imagesLoader, "world1level1"));
        enemies.add(new Mushroom(3940, 168, imagesLoader, "world1level1"));

        for (int i = 0; i < 3; i++)
            blocks.add(new RedBrick(4500 + 48 * i, 200, imagesLoader, "world1level1"));


        elements.addAll(enemies);
        elements.addAll(blocks);

    }
}
