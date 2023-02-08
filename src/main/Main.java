package main;

import ui.Login;
import util.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
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
        GamePanel game = new GamePanel();

        Login login = new Login();


        cards = new JPanel(new CardLayout());
        cards.add(game);

        contentPane.add(cards);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, e.getActionCommand());
    }
}