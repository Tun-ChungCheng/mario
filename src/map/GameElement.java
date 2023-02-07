package map;

import sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;

public abstract class GameElement {
    protected ArrayList<Sprite> elements;

    public void drawSprite(Graphics g) {
        for (Sprite element:elements) {
            element.drawSprite(g);
        }
    }

    public ArrayList<Sprite> getElements() {
        return elements;
    }
}
