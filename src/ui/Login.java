package ui;

import db.Database;
import main.Game;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends UserInterface implements ActionListener {

    public Login(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font font, Game game) {
        super(imagesLoader, cards, marioDatabase, font);
        this.game = game;
        initialCommonComponent();

        registerButton = new JButton("register");
        registerButton.setBounds(285, 550, 110, 30);
        registerButton.setFont(marioFont);
        registerButton.addActionListener(this);

        rankButton = new JButton("rank");
        rankButton.setBounds(405, 550, 80, 30);
        rankButton.setFont(marioFont);
        rankButton.addActionListener(this);

        playButton = new JButton("play");
        playButton.setFont(marioFont);
        playButton.setBounds(500, 450, 90, 80);
        playButton.addActionListener(this);

        add(registerButton);
        add(rankButton);
        add(playButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "register" -> cardLayout.show(cards, "register");
            case "rank"     -> cardLayout.show(cards, "rank");
            case "play"     -> logIn();
        }
    }

    private void logIn() {
        account = accountField.getText();
        password = String.valueOf(passwordField.getPassword());

        if (marioDatabase.logIn(account, password)) {
            game.startGame();
            cardLayout.show(cards, "play");
        } else
            JOptionPane.showMessageDialog(null, "Login fail!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
