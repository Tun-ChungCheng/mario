package ui;

import db.Database;
import main.Game;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Register extends UserInterface implements ActionListener {
    public Register(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font font, Game game) {
        super(imagesLoader, cards, marioDatabase, font);
        this.game = game;
        initialCommonComponent();

        nameLabel = new JLabel("name");
        nameLabel.setFont(marioFont);
        nameLabel.setBounds(200, 400, 150, 30);

        nameField = new JTextField();
        nameField.setFont(marioFont);
        nameField.setBounds(284, 400, 200, 30);

        playButton = new JButton("play");
        playButton.setFont(marioFont);
        playButton.setBounds(500, 450, 90, 80);
        playButton.addActionListener(this);

        loginButton = new JButton("login");
        loginButton.setBounds(285, 550, 110, 30);
        loginButton.setFont(marioFont);
        loginButton.addActionListener(this);

        rankButton = new JButton("rank");
        rankButton.setBounds(405, 550, 80, 30);
        rankButton.setFont(marioFont);
        rankButton.addActionListener(this);

        add(nameLabel);
        add(nameField);
        add(playButton);
        add(loginButton);
        add(rankButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "login" -> cardLayout.show(cards, "login");
            case "rank"  -> cardLayout.show(cards, "rank");
            case "play"  -> register();
        }
    }

    private void register() {
        name = nameField.getText();
        account = accountField.getText();
        password = String.valueOf(passwordField.getPassword());

        if (name.equals("")){
            showErrorMessage("Name can't be empty!");
            return;
        }
        else if (name.length() > 7) {
            showErrorMessage("Name's length greater than 7!");
            return;
        }
        else if (account.equals("")) {
            showErrorMessage("Account can't be empty!");
            return;
        }
        else if (password.equals("")) {
            showErrorMessage("Password can't be empty!");
            return;
        }

        if (marioDatabase.checkAccount(account)) {
            if (marioDatabase.createAccount(name, account, password)) {
                cardLayout.show(cards, "play");
                game.startGame();
            } else showErrorMessage("Add player error!");
        } else showErrorMessage("Player exist!");
    }
}
