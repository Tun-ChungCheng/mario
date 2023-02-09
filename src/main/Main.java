package main;

import db.Database;
import ui.Login;
import ui.Register;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

// https://www.mariouniverse.com/sprites-nes-smb/
public class Main implements ActionListener {
    public static final int WINDOW_WIDTH = 768;
    public static final int WINDOW_HEIGHT = 720;

    private JPanel cards;
    private static Database marioDatabase;

    public static void main(String[] args) {
        try {
            marioDatabase = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame window = new JFrame("Super Mario Bros.");
        window.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        Main main = new Main();
        main.addComponentToPane(window.getContentPane());

        window.setVisible(true);
    }

    private void addComponentToPane(Container contentPane) {
        cards = new JPanel(new CardLayout());

        Game game = new Game();
        game.addComponentListener(new FocusManager());

        Login login = new Login(cards, marioDatabase, game);
        Register register = new Register(cards, marioDatabase, game);


        cards.add(login, "login");
        cards.add(register, "register");
        cards.add(game, "start");

        contentPane.add(cards);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, e.getActionCommand());
    }
}