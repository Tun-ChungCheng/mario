package main;

import db.Database;
import ui.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

// https://www.mariouniverse.com/sprites-nes-smb/
public class Main implements ActionListener {
    JPanel cards;
    private static Database playerDatabase;

    public static void main(String[] args) {
        try {
            playerDatabase = new Database();
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
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        Main main = new Main();
        main.addComponentToPane(window.getContentPane());

        window.pack();
        window.setVisible(true);
    }

    private void addComponentToPane(Container contentPane) {
        Login login = new Login();
        login.getLoginButton().addActionListener(this);

        Game game = new Game();
        game.addComponentListener(new FocusManager());

        cards = new JPanel(new CardLayout());
        cards.add(login);
        cards.add(game, "login");

        contentPane.add(cards);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, e.getActionCommand());
    }
}