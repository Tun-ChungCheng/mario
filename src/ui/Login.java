package ui;

import db.Database;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login extends UserInterface implements ActionListener {
    private static final String LOG_IN = "SELECT * FROM players WHERE account = ?";

    protected JButton registerButton;


    public Login(JPanel cards, Database marioDatabase, Game game) {
        super(cards, marioDatabase, game);

        registerButton = new JButton("register");
        registerButton.setBounds(330, 400, 90, 30);
        registerButton.addActionListener(this);
        add(registerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        CardLayout cardLayout = (CardLayout) cards.getLayout();

        if (logIn()) {
            game.startGame();
            cardLayout.show(cards, actionCommand);
        }


        System.out.println(accountField.getText());
        System.out.println(passwordField.getText());

    }

    private boolean logIn() {
        try (PreparedStatement statement = marioDatabase.connection.prepareStatement(LOG_IN)) {
            statement.setString(1, accountField.getText());
            statement.executeQuery();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
