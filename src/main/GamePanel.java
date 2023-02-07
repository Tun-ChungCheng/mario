package main;

import sprite.Mario;
import util.Camera;
import map.MapManager;
import util.ClipsLoader;
import util.ImagesLoader;

import javax.swing.JPanel;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable{
    public static final int PANEL_WIDTH = 1344;
    public static final int PANEL_HEIGHT = 720;
    private static final int FPS = 120;
    private static final int UPS = 200;
    private static final int SPAWN_X = 200;
    private static final int SPAWN_Y = 500;
    private static final String IMAGES_INFO = "imagesInfo.txt";
    private static final String SOUNDS_INFO = "soundsInfo.txt";
    private static final String FONT_DIR = "font/mario-font.ttf";

    private Thread animator;
    private volatile boolean running  = false;
    private volatile boolean isPaused = false;
    private volatile boolean gameOver = false;

    private int world     = 1,
                level     = 1,
                countdown = 300;
    private Mario             mario;
    private Camera            camera;
    private MapManager        mapManager;
    private FontMetrics       fontMetrics;


    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        requestFocus();

        ImagesLoader imagesLoader = new ImagesLoader(IMAGES_INFO);
        ClipsLoader clipsLoader = new ClipsLoader(SOUNDS_INFO);
        clipsLoader.play("background", true);
        String currentMap = "world" + world + "level" + level;

        camera = new Camera();
        mapManager = new MapManager(imagesLoader, currentMap);
        mario = new Mario(SPAWN_X, SPAWN_Y, imagesLoader, clipsLoader, mapManager);

        addKeyListener(new InputManager(mario));
        fontMetrics = this.getFontMetrics(new Font(FONT_DIR, Font.BOLD, 36));
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
                if (!gameOver) countdown--;
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
            if (mario.isDie()) gameOver = true;
            camera.updatePosition(mario.x, mario.y);
            mario.updateSprite();
            mapManager.updateSprite();
        }
    }

    public void gameRender(Graphics g) {
        camera.render(g);
        mapManager.drawSprite(g);
        mario.drawSprite(g);
        if (gameOver) gameOverMessage(g);
        reportStatus(g);
    }

    private void reportStatus(Graphics g) {
        String record  = ("MARIO %06d    WORLD %d-%d    TIME %d")
                .formatted(mario.getScore(), world, level, countdown);
        int x = (PANEL_WIDTH - fontMetrics.stringWidth(record)) / 2;
        int y = (PANEL_HEIGHT - fontMetrics.getHeight()) / 15;

        g.setFont(fontMetrics.getFont());
        g.drawString(record, x + camera.getX(), y + camera.getY());
    }

    private void gameOverMessage(Graphics g) {
        String message = "GAME OVER";
        int x = (PANEL_WIDTH - fontMetrics.stringWidth(message)) / 2 + camera.getX();
        int y = (PANEL_HEIGHT - fontMetrics.getHeight()) / 2 + camera.getY();

        g.setColor(Color.black);
        g.fillRect(camera.getX(), camera.getY() , PANEL_WIDTH, PANEL_HEIGHT);
        g.setColor(Color.white);
        g.setFont(fontMetrics.getFont());
        g.drawString(message, x, y);
    }

}
