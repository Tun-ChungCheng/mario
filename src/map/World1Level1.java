package map;

import sprite.block.Block;
import sprite.block.ItemBrick;
import sprite.block.Pipe;
import sprite.block.RedBrick;
import sprite.enemy.Enemy;
import sprite.enemy.Goomba;
import util.ImagesLoader;

import java.util.ArrayList;

import static sprite.block.Pipe.*;

public class World1Level1 extends GameElement {
    public World1Level1(ImagesLoader imagesLoader) {
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
}
