package map;

import sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;

public abstract class GameElement {
    public ArrayList<Sprite> elements;

    public void draw(Graphics g) {
        for (Sprite element:elements) {
            element.render(g);
        }
    }
}
