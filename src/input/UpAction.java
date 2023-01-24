package input;

import main.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static constant.PlayerConstants.*;

public class UpAction extends AbstractAction {
    Game game;

    public UpAction(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.getPlayer().resetAnimationTick();

        int dir = game.getPlayer().getDirection();

        if (dir == DIR_LEFT)  game.getPlayer().setPlayAction(JUMP_LEFT);
        if (dir == DIR_RIGHT) game.getPlayer().setPlayAction(JUMP_RIGHT);
        game.getPlayer().setY(game.getPlayer().getY() - DELTA_Y);
    }
}
