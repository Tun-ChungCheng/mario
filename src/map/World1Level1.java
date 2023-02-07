package map;

import sprite.block.Block;
import sprite.block.ItemBrick;
import sprite.block.Pipe;
import sprite.block.RedBrick;
import sprite.enemy.Enemy;
import sprite.enemy.Mushroom;
import util.ImagesLoader;

import java.util.ArrayList;

import static sprite.block.Pipe.*;

public class World1Level1 extends GameElement {
    public World1Level1(ImagesLoader imagesLoader) {
        elements = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Block> blocks  = new ArrayList<>();

        blocks.add(new ItemBrick(800, 380, imagesLoader));

        blocks.add(new RedBrick(1000, 380, imagesLoader));
        blocks.add(new ItemBrick(1048, 380, imagesLoader));
        blocks.add(new RedBrick(1096, 380, imagesLoader));
        blocks.add(new ItemBrick(1144, 380, imagesLoader));
        blocks.add(new RedBrick(1192, 380, imagesLoader));

        blocks.add(new ItemBrick(1096, 180, imagesLoader));
        enemies.add(new Mushroom(1096, 480, imagesLoader));

        blocks.add(new Pipe(1384, 526, SMALL_SIZE, imagesLoader));

        blocks.add(new Pipe(1820, 495, MEDIUM_SIZE, imagesLoader));
        enemies.add(new Mushroom(1916, 480, imagesLoader));

        blocks.add(new Pipe(2210, 453, LARGE_SIZE, imagesLoader));
        enemies.add(new Mushroom(2500, 480, imagesLoader));
        enemies.add(new Mushroom(2548, 480, imagesLoader));

        blocks.add(new Pipe(2745, 453, LARGE_SIZE, imagesLoader));

        blocks.add(new RedBrick(3700, 380, imagesLoader));
        blocks.add(new ItemBrick(3748, 380, imagesLoader));
        blocks.add(new RedBrick(3796, 380, imagesLoader));

        for (int i = 0; i < 9; i++)
            blocks.add(new RedBrick(3844 + 48 * i, 200, imagesLoader));

        enemies.add(new Mushroom(3844, 168, imagesLoader));
        enemies.add(new Mushroom(3940, 168, imagesLoader));

        for (int i = 0; i < 3; i++)
            blocks.add(new RedBrick(4500 + 48 * i, 200, imagesLoader));

        blocks.add(new ItemBrick(4644, 200, imagesLoader));

        blocks.add(new RedBrick(4644, 380, imagesLoader));

        blocks.add(new RedBrick(4996, 380, imagesLoader));
        blocks.add(new RedBrick(5044, 380, imagesLoader));

        blocks.add(new ItemBrick(5236, 380, imagesLoader));
        blocks.add(new ItemBrick(5380, 380, imagesLoader));
        blocks.add(new ItemBrick(5524, 380, imagesLoader));
        blocks.add(new ItemBrick(5380, 200, imagesLoader));

        blocks.add(new RedBrick(5572, 380, imagesLoader));

        blocks.add(new RedBrick(5668, 200, imagesLoader));
        blocks.add(new RedBrick(5716, 200, imagesLoader));
        blocks.add(new RedBrick(5764, 200, imagesLoader));

        enemies.add(new Mushroom(5812, 480, imagesLoader));
        enemies.add(new Mushroom(5860, 480, imagesLoader));
        enemies.add(new Mushroom(5956, 480, imagesLoader));
        enemies.add(new Mushroom(6004, 480, imagesLoader));

        blocks.add(new RedBrick(6004, 200, imagesLoader));
        blocks.add(new RedBrick(6052, 200, imagesLoader));



        elements.addAll(enemies);
        elements.addAll(blocks);

    }
}
