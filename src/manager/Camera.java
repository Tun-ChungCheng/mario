package manager;

import main.GamePanel;

import java.awt.Graphics;


import static manager.MapManager.MAP_HEIGHT;
import static manager.MapManager.MAP_WIDTH;

public class Camera {
    private int x, y;
    private int offsetMaxX = MAP_WIDTH - GamePanel.PANEL_WIDTH;
    private int offsetMaxY = MAP_HEIGHT - GamePanel.PANEL_HEIGHT;
    private int offsetMinX = 0;
    private int offsetMinY = 0;

    public Camera() {}

    public void update(int playerX, int playerY) {
        x = playerX - (GamePanel.PANEL_WIDTH / 4);
        y = playerY - (GamePanel.PANEL_HEIGHT / 2);

        if (x > offsetMaxX) x = offsetMaxX;
        else if (x < offsetMinX) x = offsetMinX;
        if (y > offsetMaxY) y = offsetMaxY;
        else if (y < offsetMinY) y = offsetMinY;
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
