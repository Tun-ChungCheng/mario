package ui;

import db.Database;
import util.ImagesLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Main.WINDOW_HEIGHT;
import static main.Main.WINDOW_WIDTH;

public class Rank extends UserInterface implements ActionListener {
    public Rank(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font game) {
        super(imagesLoader, cards, marioDatabase, game);

        Object[][] data = marioDatabase.getTopTenPlayers();
        String[] columnNames = {"RANK", "NAME", "SCORE"};

        loginButton = new JButton("login");
        loginButton.setBounds(285, 550, 110, 30);
        loginButton.setFont(marioFont);
        loginButton.addActionListener(this);

        registerButton = new JButton("register");
        registerButton.setBounds(405, 550, 110, 30);
        registerButton.setFont(marioFont);
        registerButton.addActionListener(this);

        rankTable = new JTable(data, columnNames);
        rankTable.setBounds(200, 50, 500, 500);
        rankTable.setForeground(Color.white);
        rankTable.setFont(marioFont);
        rankTable.setRowHeight(50);
        rankTable.setShowGrid(false);
        rankTable.setOpaque(false);
        rankTable.setColumnSelectionAllowed(false);
        ((DefaultTableCellRenderer)rankTable.getDefaultRenderer(Object.class)).setOpaque(false);

        add(loginButton);
        add(registerButton);
        add(rankTable);

        //https://stackoverflow.com/questions/17232118/java-update-database-on-jtable-cell-change
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(mapImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "register" -> cardLayout.show(cards, "register");
            case "login"     -> cardLayout.show(cards, "login");
        }
    }
}
