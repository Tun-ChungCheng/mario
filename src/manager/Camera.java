package manager;

import java.awt.*;


import static main.GamePanel.GAME_HEIGHT;
import static main.GamePanel.GAME_WIDTH;
import static manager.MapManager.MAP_HEIGHT;
import static manager.MapManager.MAP_WIDTH;

public class Camera {
    private int x, y;
    private int offsetMaxX = MAP_WIDTH - GAME_WIDTH;
    private int offsetMaxY = MAP_HEIGHT - GAME_HEIGHT;
    private int offsetMinX = 0;
    private int offsetMinY = 0;

    public Camera() {}

    public void update(int playerX, int playerY) {
        x = playerX - (GAME_WIDTH / 4);
        y = playerY - (GAME_HEIGHT / 2);

        if (x > offsetMaxX) x = offsetMaxX;
        else if (x < offsetMinX) x = offsetMinX;
        if (y > offsetMaxY) y = offsetMaxY;
        else if (y < offsetMinY) y = offsetMinY;
    }

    public void render(Graphics g) {
        g.translate(-x, -y);
    }
}
