package ui;

import db.Database;
import db.Player;
import main.Game;
import util.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        String account = accountField.getText();
        String password = passwordField.getPassword().toString();

        if (logIn(account, password)) {
            game.startGame();
            cardLayout.show(cards, "game");
        }


        System.out.println(accountField.getText());
        System.out.println(passwordField.getPassword());

    }

    private boolean logIn(String account, String password) {
        try (PreparedStatement statement = marioDatabase.connection.prepareStatement(LOG_IN)) {
            statement.setString(1, account);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (BCrypt.checkpw(password, resultSet.getString("password"))) {
                    Player player = new Player( resultSet.getInt("id"),
                                                resultSet.getString("name"),
                                                resultSet.getString("account"),
                                                resultSet.getString("score") );
                    System.out.println(player.getId());
                    System.out.println(player.getName());
                    System.out.println(player.getAccount());
                    System.out.println(player.getScore());

                    return true;
                } else return false;

            } else return false;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
