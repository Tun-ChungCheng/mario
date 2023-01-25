package manager;

import entity.Player;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static constant.PlayerConst.*;


public class InputManager implements KeyListener {
    private GamePanel gamePanel;
    private Player player;
    private int dir;

    public InputManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        player = gamePanel.getGame().getPlayer();
        dir = player.getDirection();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if (dir == DIR_LEFT)  player.setPlayAction(JUMP_LEFT);
                if (dir == DIR_RIGHT) player.setPlayAction(JUMP_RIGHT);
            }
            case KeyEvent.VK_RIGHT -> {
                player.setPlayAction(RUN_RIGHT);
                player.setDirection(DIR_RIGHT);
            }
            case KeyEvent.VK_DOWN -> {
                player.setPlayAction(DIE);
            }
            case KeyEvent.VK_LEFT -> {
                player.setPlayAction(RUN_LEFT);
                player.setDirection(DIR_LEFT);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if (dir == DIR_RIGHT) player.setPlayAction(IDLE_RIGHT);
                if (dir == DIR_LEFT)  player.setPlayAction(IDLE_LEFT);
                player.resetAnimationTick();
            }
            case KeyEvent.VK_RIGHT -> {
                player.setPlayAction(IDLE_RIGHT);
                player.resetAnimationTick();
            }
            case KeyEvent.VK_DOWN -> {
                if (dir == DIR_RIGHT) player.setPlayAction(IDLE_RIGHT);
                if (dir == DIR_LEFT)  player.setPlayAction(IDLE_LEFT);
                player.resetAnimationTick();
            }
            case KeyEvent.VK_LEFT -> {
                player.setPlayAction(IDLE_LEFT);
                player.resetAnimationTick();
            }
        }
    }
}