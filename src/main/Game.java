package main;

import db.Database;
import map.Map;
import sprite.Mario;
import ui.Rank;
import util.Camera;
import util.ClipsLoader;
import util.ImagesLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static main.Main.WINDOW_HEIGHT;
import static main.Main.WINDOW_WIDTH;


public class Game extends JPanel implements Runnable{
    private static final int FPS = 120;
    private static final int UPS = 200;
    private static final int SPAWN_X = 200;
    private static final int SPAWN_Y = 550;
    private static final String SOUNDS_INFO = "soundsInfo.txt";

    private Thread animator;
    private volatile boolean running  = false;
    private volatile boolean gameOver = false;

    private boolean scoreSaved = false;
    private int countdown = 120;
    private final Mario       mario;
    private final Camera      camera;
    private final Map map;
    private final ClipsLoader clipsLoader;
    private final FontMetrics fontMetrics;
    private final Database    marioDatabase;
    private final JPanel      cards;
    private final CardLayout  cardLayout;
    private final Rank        rank;


    public Game(ImagesLoader imagesLoader, JPanel cards, Database marioDatabase, Font marioFont, Rank rank) {
        setFocusable(true);
        this.marioDatabase = marioDatabase;
        this.rank = rank;
        this.cards = cards;
        cardLayout = (CardLayout) cards.getLayout();

        clipsLoader = new ClipsLoader(SOUNDS_INFO);
        camera      = new Camera();
        map = new Map(imagesLoader);
        mario       = new Mario(SPAWN_X, SPAWN_Y, imagesLoader, clipsLoader, map);
        fontMetrics = this.getFontMetrics(marioFont.deriveFont(20f));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> mario.setUp(true);
                    case KeyEvent.VK_RIGHT -> mario.setRight(true);
                    case KeyEvent.VK_LEFT -> mario.setLeft(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> mario.setUp(false);
                    case KeyEvent.VK_RIGHT -> mario.setRight(false);
                    case KeyEvent.VK_LEFT  -> mario.setLeft(false);
                }
            }
        });
    }

    public void startGame() {
        if (animator == null || !running) {
            clipsLoader.play("background", true);

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
                if (!gameOver && countdown > 0 && mario.x < 9000) countdown--;
                else gameOver();
            }
        }
        System.exit(0);
    }

    public void gameUpdate() {
        if (!gameOver) {
            if (mario.isDie() || countdown == 0)
                new Timer(3000, (e) -> gameOver = true).start();
            camera.updatePosition(mario.x, mario.y);
            mario.updateSprite();
            map.updateSprite();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameRender(g);
    }

    public void gameRender(Graphics g) {
        camera.render(g);
        map.drawSprite(g);
        mario.drawSprite(g);
        if (gameOver) gameOverMessage(g);
        reportStatus(g);
    }

    private void gameOverMessage(Graphics g) {
        clipsLoader.play("gameOver", false);

        String message = "GAME OVER";
        int x = (WINDOW_WIDTH - fontMetrics.stringWidth(message)) / 2 + camera.getX();
        int y = (WINDOW_HEIGHT - fontMetrics.getHeight()) / 2 + camera.getY();

        g.setColor(Color.black);
        g.fillRect(camera.getX(), camera.getY() , WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.white);
        g.setFont(fontMetrics.getFont());
        g.drawString(message, x, y);
    }

    private void reportStatus(Graphics g) {
        String playerName = marioDatabase.getPlayer().getName();
        String record  = ("%s %06d WORLD RANDOM TIME %d")
                .formatted(playerName, mario.getScore(), countdown);
        int x = (WINDOW_WIDTH - fontMetrics.stringWidth(record)) / 2;
        int y = (WINDOW_HEIGHT - fontMetrics.getHeight()) / 12;

        g.setColor(Color.white);
        g.setFont(fontMetrics.getFont());
        g.drawString(record, x + camera.getX(), y + camera.getY());
    }

    private void gameOver() {
        if (!scoreSaved) {
            scoreSaved = true;
            saveScore();
            updateRank();
            toRankPage();
        }
    }

    private void saveScore() {
        marioDatabase.updateScore(mario.getScore());
    }

    private void updateRank() {
        rank.updateModel();
    }

    private void toRankPage() {
        new Timer(1500, (e) -> cardLayout.show(cards, "rank")).start();
    }

}
