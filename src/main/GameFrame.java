package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame implements WindowListener{
    private GamePanel gamePanel;

    public GameFrame() {
        super("Super Mario Bro.");

        gamePanel = new GamePanel();
        add(gamePanel);

        addWindowListener(this);
        pack();
        setResizable(false);
        setVisible(true);
    }

    // ----------------- window listener methods -------------

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        gamePanel.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        gamePanel.resumeGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        gamePanel.pauseGame();
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    // ----------------------------------------------------
}
