package main;


import entity.Player;
import manager.Camera;
import manager.MapManager;

import java.awt.*;

import static constant.GameConst.FPS;
import static constant.GameConst.UPS;


public class Game implements Runnable{

    private GameFrame  gameFrame;
    private GamePanel  gamePanel;
    private Thread     gameThread;
    private Player     player;
    private MapManager mapManager;
    private Camera     camera;

    public Game() {
        mapManager = new MapManager();
        player = new Player(200, 500, 64, 64);
        camera = new Camera();
        player.loadMapData(mapManager.getMapData());

        gamePanel = new GamePanel(this);
        gameFrame = new GameFrame(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }


    @Override
    public void run() {
        int    updates       = 0;
        int    frames        = 0;
        double deltaU        = 0;
        double deltaF        = 0;
        double timePerFrame  = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        long   previousTime  = System.nanoTime();
        long   lastCheck     = System.currentTimeMillis();

        do {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.printf("FPS = %d | UPS = %d%n", frames, updates);
                frames = updates = 0;
            }
        } while (true);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        camera.update(player.x, player.y);
        mapManager.update();
        player.update();
    }

    public void render(Graphics g) {
        camera.render(g);
        mapManager.draw(g);
        player.render(g);
    }

    public Player getPlayer() { return player; }

}
