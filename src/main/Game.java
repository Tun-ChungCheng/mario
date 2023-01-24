package main;


import entity.Player;

import java.awt.*;


public class Game implements Runnable{
    private static final int FPS = 120;
    private static final int UPS = 200;
    private static final int TILES_DEFAULT_SIZE = 32;
    private static final float SCALE = 1.5f;
    private static final int TILES_IN_WIDTH = 26;
    private static final int TILES_IN_HEIGHT = 14;
    private static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public  static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public  static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private GameFrame         gameFrame;
    private GamePanel         gamePanel;
    private Thread            gameThread;
    private Player            player;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameFrame = new GameFrame(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        player = new Player(100, 100, 100, 100);
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
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }

    public Player getPlayer() { return player; }
}
