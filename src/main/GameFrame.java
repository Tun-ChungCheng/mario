package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame {
    private JFrame window;

    public GameFrame(GamePanel gamePanel) {
        window = new JFrame("Super Mario Bro.");

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(gamePanel);
        window.pack();

        window.setVisible(true);
    }
}