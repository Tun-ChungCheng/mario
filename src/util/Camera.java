package util;


import java.awt.Graphics;


import static main.Main.WINDOW_HEIGHT;
import static main.Main.WINDOW_WIDTH;
import static map.Map.MAP_HEIGHT;
import static map.Map.MAP_WIDTH;

public class Camera {
    private int x, y;
    private final int offsetMaxX;
    private final int offsetMaxY;
    private final int offsetMinX;
    private final int offsetMinY;

    public Camera() {
        offsetMaxX = MAP_WIDTH - WINDOW_WIDTH;
        offsetMaxY = MAP_HEIGHT - WINDOW_HEIGHT;
        offsetMinX = 0;
        offsetMinY = 0;
    }

    public void updatePosition(int playerX, int playerY) {
        x = playerX - (WINDOW_WIDTH  / 4);
        y = playerY - (WINDOW_HEIGHT / 2);

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
