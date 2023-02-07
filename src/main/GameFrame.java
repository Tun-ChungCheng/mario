package main;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    GamePanel gamePanel;

    public GameFrame() {
        super("Super Mario Bros.");

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
