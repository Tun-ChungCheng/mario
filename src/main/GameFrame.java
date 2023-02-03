package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame{
    private GamePanel gamePanel;

    public GameFrame() {
        super("Super Mario Bro.");

        gamePanel = new GamePanel();
        
        add(gamePanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
