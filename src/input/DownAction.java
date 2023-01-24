package input;

import main.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static constant.PlayerConstants.*;

public class DownAction extends AbstractAction {
    Game game;

    public DownAction(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.getPlayer().setPlayAction(DIE);
        game.getPlayer().setY(game.getPlayer().getY() + DELTA_Y);
    }
}
