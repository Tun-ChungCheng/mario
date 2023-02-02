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
    private static final int SPAWN_X = 200;
    private static final int SPAWN_Y = 500;
    private static final String IMAGES_INFO = "imagesInfo.txt";
    private static final String MARIO_FONT = "C:\\Users\\User\\Desktop\\super_mario_bros_clone\\font\\mario-font.ttf";

    private Thread animator;
    private volatile boolean running  = false;
    private volatile boolean isPaused = false;
    private volatile boolean gameOver = false;
    private int score = 0;

    private Player player;
    private ArrayList<Enemy> enemies;
    private Camera camera;
    private MapManager mapManager;
    private FontMetrics fontMetrics;
    private Font marioFont;


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
            enemies.add(new Enemy(randInt(SPAWN_X, 1500), SPAWN_Y,
                    imagesLoader, "mushroom", "map1"));
        }
        player = new Player(SPAWN_X, SPAWN_Y, imagesLoader, soundManager, "map1");

        addKeyListener(new InputManager(player));
        marioFont = new Font(MARIO_FONT, Font.PLAIN, 24);
        fontMetrics = this.getFontMetrics(marioFont);
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
                if (!enemy.isDie()) player.setDie();
            }
        }
    }

    public void gameRender(Graphics g) {
        camera.render(g);
        mapManager.draw(g);
        player.render(g);
        enemies.forEach(enemy -> enemy.render(g));
        reportStatus(g);

    }

    private void reportStatus(Graphics g) {
        StringBuilder whiteSpace = new StringBuilder();
        for (int i = 0; i < 120; i++) whiteSpace.append(" ");

        String title  = ("MARIO" + whiteSpace + "WORLD      TIME");
        String record = ("%06d"  + whiteSpace + "%d-%d        %d").formatted(score, 1, 1, 30);
        int x = (PANEL_WIDTH - fontMetrics.stringWidth(title)) / 2;
        int y = (PANEL_HEIGHT - fontMetrics.getHeight()) / 14;
        g.setFont(marioFont);
        g.drawString(title, x + camera.getX(), y + camera.getY());
        g.drawString(record, x + camera.getX(), y + fontMetrics.getHeight() + camera.getY());
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
