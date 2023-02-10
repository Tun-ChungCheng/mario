package ui;

import db.Database;
import main.Game;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Register extends UserInterface implements ActionListener {
    private JButton loginButton;

    private JLabel nameLabel;
    private String name;
    private CardLayout cardLayout;
    private Game game;

    public Register(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font font, Game game) {
        super(imagesLoader, cards, marioDatabase, font);
        this.game = game;
        addSameComponentToPane();

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

        add(nameLabel);
        add(nameField);
        add(playButton);
        add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        name = nameField.getText();
        account = accountField.getText();
        password = String.valueOf(passwordField.getPassword());
        cardLayout = (CardLayout) cards.getLayout();
        String action = e.getActionCommand();

        switch (action) {
            case "login" -> cardLayout.show(cards, "login");
            case "play"  -> register();
        }
    }

    private void register() {
        if (marioDatabase.checkAccount(account)) {
            if (marioDatabase.addPlayer(name, account, password)) {
                cardLayout.show(cards, "play");
                game.startGame();
            } else
                JOptionPane.showMessageDialog(null, "Add player error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(name.equals(""))
            JOptionPane.showMessageDialog(null, "Name can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        else if(account.equals(""))
            JOptionPane.showMessageDialog(null, "Account can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        else if(password.equals(""))
            JOptionPane.showMessageDialog(null, "Password can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Player exist!", "Error", JOptionPane.ERROR_MESSAGE);
        }
}
