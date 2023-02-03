package manager;

import sprite.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class InputManager implements KeyListener {
    private Player player;

    public InputManager(Player player){
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if(!player.isJump()) {
                    player.setUp(true);
                    player.playJumpSound();
                }
            }
            case KeyEvent.VK_RIGHT -> {
                player.setRight(true);
                player.setCurrentX(player.x);
            }
            case KeyEvent.VK_DOWN -> player.setDown(true);
            case KeyEvent.VK_LEFT -> {
                player.setLeft(true);
                player.setCurrentX(player.x);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> player.setUp(false);
            case KeyEvent.VK_RIGHT -> {
                player.setRight(false);
                player.setPreviousX(player.x);
            }
            case KeyEvent.VK_DOWN -> player.setDown(false);
            case KeyEvent.VK_LEFT -> {
                player.setLeft(false);
                player.setPreviousX(player.x);
            }
        }
    }
}