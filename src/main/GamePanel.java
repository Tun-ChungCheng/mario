package main;

import manager.Camera;
import manager.InputManager;
import map.MapManager;
import sprite.Block;
import sprite.Enemy;
import sprite.Player;
import manager.SoundManager;
import sprite.Sprite;
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
    private int score = 0, world = 1, level = 1, countdown = 300, live = 3;

    private Player player;
    private ArrayList<Sprite> elements;
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
        String currentMap = "world" + world + "level" + level;


        mapManager = new MapManager(imagesLoader, currentMap);
        elements = mapManager.gameElement.elements;
        camera = new Camera();
        player = new Player(SPAWN_X, SPAWN_Y, imagesLoader, soundManager, currentMap);

        addKeyListener(new InputManager(player));
        marioFont = new Font(MARIO_FONT, Font.PLAIN, 24);
        fontMetrics = this.getFontMetrics(marioFont);
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
                countdown--;
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
            elements.forEach(element -> element.update());
            checkCollision();
        }
    }

    private void checkCollision() {
        for (Sprite element: elements) {
            String superClassName = element.getClass().getSuperclass().getSimpleName();
            switch (superClassName) {
                case "Enemy" -> {
                    Enemy enemy = (Enemy) element;
                    if (!enemy.isDie() && player.intersects(enemy)) {
                        if (player.isJump()) enemy.setDie();
                        if (!enemy.isDie()) player.setDie();
                    }
                }
                case "Block" -> {
                    Block block = (Block) element;
                    if (player.intersects(block)) {
                        if (player.y < block.y) {
                            player.y = block.y - player.height;
                            player.setJump(false);
                        }
                        if (player.y > block.y) {
                            if (player.y <= block.y + block.height) player.y = block.y + block.height;
                        }
                    }

                }
            }

        }
    }

    public void gameRender(Graphics g) {
        camera.render(g);
        mapManager.draw(g);
        player.render(g);
        reportStatus(g);

    }

    private void reportStatus(Graphics g) {
        String title  = ("MARIO" + getWhiteSpace(30) + "WORLD" + getWhiteSpace(30) + "TIME" + getWhiteSpace(30) + "LIVES");
        String record = ("%06d"  + getWhiteSpace(35) + "%d-%d" + getWhiteSpace(35) + "%d"   + getWhiteSpace(35) + "%d")
                .formatted(score, world, level, countdown, live);

        int x = (PANEL_WIDTH - fontMetrics.stringWidth(title)) / 2;
        int y = (PANEL_HEIGHT - fontMetrics.getHeight()) / 15;

        g.setFont(marioFont);
        g.drawString(title,  x + camera.getX(), y + camera.getY());
        g.drawString(record, x + camera.getX(), y + camera.getY() + fontMetrics.getHeight());
    }

    private String getWhiteSpace(int n) {
        StringBuilder whiteSpaces = new StringBuilder();
        return whiteSpaces.append(" ".repeat(Math.max(0, n))).toString();
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
