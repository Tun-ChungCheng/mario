package input;

import main.Game;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import static constant.PlayerConstants.*;

public class LeftAction extends AbstractAction {
    Game game;

    public LeftAction(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int playerAction = game.getPlayer().getPlayAction();

        if (playerAction != RUN_LEFT) game.getPlayer().resetAnimationTick();

        game.getPlayer().setPlayAction(RUN_LEFT);
        game.getPlayer().setDirection(DIR_LEFT);
        game.getPlayer().setX(game.getPlayer().getX() - DELTA_X);
    }
}
