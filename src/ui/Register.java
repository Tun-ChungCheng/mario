package ui;

import db.Database;
import main.Game;
import util.BCrypt;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static main.Main.WINDOW_HEIGHT;
import static main.Main.WINDOW_WIDTH;


public class Register extends UserInterface implements ActionListener {

    private static final String CHECK_ACCOUNT = "SELECT count(*) count FROM players WHERE account = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO players (name, account, password) VALUES (?, ?, ?)";

    public Register(JPanel cards, Database marioDatabase, Game game) {
        super(cards, marioDatabase, game);

        nameField = new JTextField();
        nameField.setBounds(284, 400, FIELD_WIDTH, FIELD_HEIGHT);
        add(nameField);

        startButton.addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(signatureImage, 230, 400, 30, 30, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();

        if (checkAccount()) {
            if (addMember()) {
                cardLayout.show(cards, "game");
                game.startGame();
            }
            else System.out.println("account exist");
        } else System.out.println("account exist");
    }

    private boolean checkAccount() {
        try (PreparedStatement statement = marioDatabase.connection.prepareStatement(CHECK_ACCOUNT)) {
            statement.setString(1, accountField.getText());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt("count") == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addMember() {
        try (PreparedStatement statement = marioDatabase.connection.prepareStatement(INSERT_ACCOUNT)) {
            statement.setString(1, nameField.getText());
            statement.setString(2, accountField.getText());
            statement.setString(3, BCrypt.hashpw(passwordField.getPassword().toString(), BCrypt.gensalt()));
            System.out.println(BCrypt.hashpw(passwordField.getPassword().toString(), BCrypt.gensalt()));
            if (statement.executeUpdate() != 0) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
