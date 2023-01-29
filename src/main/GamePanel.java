package main;

import manager.Camera;
import manager.InputManager;
import manager.MapManager;
import sprite.Player;
import util.ClipsLoader;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;



public class GamePanel extends JPanel implements Runnable{
    public static final int GAME_WIDTH = 1344;
    public static final int GAME_HEIGHT = 720;
    public static final int FPS = 120;
    public static final int UPS = 200;

    private static final String IMAGES_INFO = "imagesInfo.txt";
    private static final String SOUNDS_INFO = "soundsInfo.txt";

    private Thread animator;
    private volatile boolean running  = false;
    private volatile boolean isPaused = false;

    private volatile boolean gameOver = false;
    private int score = 0;

    private Player     player;
    private MapManager mapManager;
    private Camera     camera;

    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setFocusable(true);
        requestFocus();

        ImagesLoader imagesLoader = new ImagesLoader(IMAGES_INFO);
        ClipsLoader clipsLoader = new ClipsLoader(SOUNDS_INFO);

        mapManager = new MapManager();
        player = new Player(200, 500, 64, 64, imagesLoader);
        camera = new Camera();
        player.loadMapData(mapManager.getMapData());

        addKeyListener(new InputManager(player));
    }

    public void addNotify() {
        super.addNotify();
        startGame();
    }

    private void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
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

        running = true;

        while (running){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                gameUpdate();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.printf("FPS = %d | UPS = %d%n", frames, updates);
                frames = updates = 0;
            }
        }
        System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameRender(g);
    }

    public void gameUpdate() {
        camera.update(player.getX(), player.getY());
        mapManager.update();
        player.update();
    }

    public void gameRender(Graphics g) {
        camera.render(g);
        mapManager.draw(g);
        player.render(g);
    }

    public void resumeGame() {
        isPaused = false;
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void stopGame() {
        running = false;
    }
}
