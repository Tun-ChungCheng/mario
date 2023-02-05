package manager;

import main.GamePanel;

import java.awt.Graphics;


import static map.MapManager.MAP_HEIGHT;
import static map.MapManager.MAP_WIDTH;

public class Camera {
    private int x, y;
    private int offsetMaxX = MAP_WIDTH - GamePanel.PANEL_WIDTH;
    private int offsetMaxY = MAP_HEIGHT - GamePanel.PANEL_HEIGHT;
    private int offsetMinX = 0;
    private int offsetMinY = 0;


    public void update(int playerX, int playerY) {
        x = playerX - (GamePanel.PANEL_WIDTH / 4);
        y = playerY - (GamePanel.PANEL_HEIGHT / 2);

        x = Math.min(offsetMaxX, Math.max(offsetMinX, x));
        y = Math.min(offsetMaxY, Math.max(offsetMinY, y));
    }

    public void render(Graphics g) {
        g.translate(-x, -y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
