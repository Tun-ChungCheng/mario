package main;

import manager.Camera;
import manager.InputManager;
import manager.MapManager;
import sprite.Enemy;
import sprite.Player;
import manager.SoundManager;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable{
    public static final int PANEL_WIDTH = 1344;
    public static final int PANEL_HEIGHT = 720;
    private static final int FPS = 120;
    private static final int UPS = 200;
    private static final String IMAGES_INFO = "imagesInfo.txt";

    private Thread animator;
    private volatile boolean running  = false;
    private volatile boolean isPaused = false;
    private volatile boolean gameOver = false;
    private int score = 0;

    private Player player;
    private ArrayList<Enemy> enemies;
    private Camera camera;
    private MapManager mapManager;


    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        requestFocus();

        ImagesLoader imagesLoader = new ImagesLoader(IMAGES_INFO);
        SoundManager soundManager = new SoundManager();
        soundManager.startBackgroundMusic();

        mapManager = new MapManager();
        camera = new Camera();
        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(randInt(200, 1500), 500, imagesLoader, "mushroom", "map1"));
        }
        player = new Player(200, 500, imagesLoader, soundManager, "map1", enemies);

        addKeyListener(new InputManager(player));
    }

    private int randInt(int max, int min) {
        return min + (int)(Math.random() * (max - min + 1));
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
        if (!gameOver && !isPaused) {
            camera.update(player.x, player.y);
            player.update();
            enemies.forEach(enemy -> enemy.update());
            checkCollision();
        }
    }

    private void checkCollision() {
        for (Enemy enemy: enemies) {
            if (player.intersects(enemy)) {
                if (player.y <= enemy.y && player.isJump()) enemy.setDie();
                player.setDie();
            }
        }
    }

    public void gameRender(Graphics g) {
        camera.render(g);
        mapManager.draw(g);
        player.render(g);
        enemies.forEach(enemy -> enemy.render(g));
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
