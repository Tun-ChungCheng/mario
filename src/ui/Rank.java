package ui;

import db.Database;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;

public class Rank extends UserInterface{
    public Rank(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font game) {
        super(imagesLoader, cards, marioDatabase, game);
//        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.printComponent(g);
    }
}
