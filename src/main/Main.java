package main;

import db.Database;
import ui.Login;
import ui.Rank;
import ui.Register;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Objects;


public class Main implements ActionListener {
    public static final int WINDOW_WIDTH = 768;
    public static final int WINDOW_HEIGHT = 720;
    private static final String IMAGES_INFO = "imagesInfo.txt";
    private static final String ICON_DIR = "/images/marioIcon.png";
    private static final String FONT_DIR = "/font/mario.ttf";

    private static Database marioDatabase;
    private JPanel cards;


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
        ImageIcon icon = new ImageIcon(ICON_DIR);

        JFrame window = new JFrame("Super Mario Bros.");
        window.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setIconImage(icon.getImage());

        Main main = new Main();
        main.addComponentToPane(window.getContentPane());

        window.setVisible(true);
    }

    private void addComponentToPane(Container contentPane) {
        Font marioFont = null;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(FONT_DIR);
            marioFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        ImagesLoader imagesLoader = new ImagesLoader(IMAGES_INFO);
        cards                     = new JPanel      (new CardLayout());
        Rank rank                 = new Rank        (imagesLoader, cards, marioDatabase, marioFont);
        Game game                 = new Game        (imagesLoader, cards, marioDatabase, marioFont, rank);
        Login login               = new Login       (imagesLoader, cards, marioDatabase, marioFont, game);
        Register register         = new Register    (imagesLoader, cards, marioDatabase, marioFont, game);

        game.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                Component source = (Component) e.getSource();
                source.requestFocusInWindow();
            }
        });

        cards.add(login,    "login");
        cards.add(register, "register");
        cards.add(game,     "play");
        cards.add(rank,     "rank");

        contentPane.add(cards);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, e.getActionCommand());
    }
}