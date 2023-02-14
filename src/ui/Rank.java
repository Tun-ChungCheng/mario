package ui;

import db.Database;
import util.ImagesLoader;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Main.WINDOW_HEIGHT;
import static main.Main.WINDOW_WIDTH;

public class Rank extends UserInterface implements ActionListener {
    public Rank(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font game) {
        super(imagesLoader, cards, marioDatabase, game);

        rankTable = new JTable(new RankTableModel());
        rankTable.setBounds(150, 50, 500, 500);
        rankTable.setForeground(Color.white);
        rankTable.setFont(marioFont.deriveFont(30f));
        rankTable.setRowHeight(80);
        rankTable.setShowGrid(false);
        rankTable.setOpaque(false);
        rankTable.setCellSelectionEnabled(false);
        ((DefaultTableCellRenderer)rankTable.getDefaultRenderer(Object.class)).setOpaque(false);

        add(rankTable);
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
            case "login"    -> cardLayout.show(cards, "login");
        }
    }

    public void updateModel() {
        rankTable.setModel(new RankTableModel());
    }

    private class RankTableModel extends AbstractTableModel {
        private final Object[][] data = marioDatabase.getTopFivePlayers();


        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
    }
}
