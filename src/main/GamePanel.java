package main;

import manager.*;

import javax.swing.*;
import java.awt.*;

import static constant.GameConst.GAME_HEIGHT;
import static constant.GameConst.GAME_WIDTH;


public class GamePanel extends JPanel{
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        addKeyListener(new InputManager(this));
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }


}
