package main;

import sprite.Mario;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class InputManager implements KeyListener {
    private Mario mario;

    public InputManager(Mario mario){
        this.mario = mario;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> mario.setUp(true);
            case KeyEvent.VK_RIGHT -> mario.setRight(true);
            case KeyEvent.VK_DOWN -> mario.setDown(true);
            case KeyEvent.VK_LEFT -> mario.setLeft(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> mario.setUp(false);
            case KeyEvent.VK_RIGHT -> mario.setRight(false);
            case KeyEvent.VK_DOWN  -> mario.setDown(false);
            case KeyEvent.VK_LEFT  -> mario.setLeft(false);
        }
    }
}