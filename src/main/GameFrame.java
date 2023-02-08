package main;

import util.Database;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    GamePanel gamePanel;

    public GameFrame() {
        super("Super Mario Bros.");

        gamePanel = new GamePanel();
        add(gamePanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
