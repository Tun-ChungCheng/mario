package util;

import main.Game;

import java.awt.Graphics;


import static map.MapManager.MAP_HEIGHT;
import static map.MapManager.MAP_WIDTH;

public class Camera {
    private int x, y;
    private int offsetMaxX = MAP_WIDTH - Game.PANEL_WIDTH;
    private int offsetMaxY = MAP_HEIGHT - Game.PANEL_HEIGHT;
    private int offsetMinX = 0;
    private int offsetMinY = 0;

    public void updatePosition(int playerX, int playerY) {
        x = playerX - (Game.PANEL_WIDTH / 4);
        y = playerY - (Game.PANEL_HEIGHT / 2);

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
