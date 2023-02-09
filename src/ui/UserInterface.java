package ui;

import db.Database;
import main.Game;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Main.WINDOW_HEIGHT;
import static main.Main.WINDOW_WIDTH;

public abstract class UserInterface extends JPanel {
    protected static final int SCALE = 3;
    protected static final int FIELD_WIDTH = 200;
    protected static final int FIELD_HEIGHT = 30;

    protected static final int BUTTON_WIDTH = 90;
    protected static final int BUTTON_HEIGHT = 30;

    protected JPanel cards;
    protected JButton startButton;
    protected JTextField accountField, nameField;
    protected JPasswordField passwordField;
    protected BufferedImage titleImage, signatureImage, userImage, passwordKeyImage, mapImage;
    protected Game game;
    protected Database marioDatabase;

    private ImagesLoader imagesLoader;

    public UserInterface(JPanel cards, Database marioDatabase, Game game) {
        this.cards = cards;
        this.marioDatabase = marioDatabase;
        this.game = game;

        imagesLoader = new ImagesLoader("userInterfaceInfo.txt");

        setImage();
        setLayout(null);

        accountField = new JTextField();
        accountField.setBounds(284, 450, FIELD_WIDTH, FIELD_HEIGHT);

        passwordField = new JPasswordField();
        passwordField.setBounds(284, 500, FIELD_WIDTH, FIELD_HEIGHT);

        startButton = new JButton("start");
        startButton.setBounds(330, 550, BUTTON_WIDTH, BUTTON_HEIGHT);

        add(accountField);
        add(passwordField);
        add(startButton);
    }


    private void setImage() {
        titleImage = imagesLoader.getImage("title");
        signatureImage = imagesLoader.getImage("signature");
        userImage = imagesLoader.getImage("user");
        passwordKeyImage = imagesLoader.getImage("passwordKey");
        mapImage = imagesLoader.getImage("world1level1")
                .getSubimage(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = titleImage.getWidth() * SCALE;
        int height = titleImage.getHeight() * SCALE;

        g.drawImage(mapImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g.drawImage(titleImage, (WINDOW_WIDTH - width) / 2, (WINDOW_HEIGHT - height) / 4,
                width, height, null);
        g.drawImage(userImage, 230, 450, 30, 30, null);
        g.drawImage(passwordKeyImage, 230, 500, 30, 30, null);
    }

}
