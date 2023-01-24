package main;

import input.DownAction;
import input.LeftAction;
import input.RightAction;
import input.UpAction;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel{
    private Game game;
    private Action upAction;
    private Action rightAction;
    private Action downAction;
    private Action leftAction;




    public GamePanel(Game game) {
        this.game = game;
        //addMouseListener(new MouseInput(this));
        //addKeyListener(new KeyboardInput(this));
        initClasses();
        keyBinding();

        System.out.println(" " + (int)GAME_WIDTH + " " +  (int)GAME_HEIGHT);
        setPanelSize();
    }

    private void initClasses() {
        upAction    = new UpAction(game);
        rightAction = new RightAction(game);
        downAction  = new DownAction(game);
        leftAction  = new LeftAction(game);
    }

    private void keyBinding() {
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", upAction);
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", rightAction);
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", downAction);
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", leftAction);
    }

    private void setPanelSize() {
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
