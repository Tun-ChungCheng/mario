package input;

import main.Game;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import static constant.PlayerConstants.*;

public class RightAction extends AbstractAction {
    Game game;

    public RightAction(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int playerAction = game.getPlayer().getPlayAction();

        if (playerAction != RUN_RIGHT) game.getPlayer().resetAnimationTick();

        game.getPlayer().setPlayAction(RUN_RIGHT);
        game.getPlayer().setDirection(DIR_RIGHT);
        game.getPlayer().setX(game.getPlayer().getX() + DELTA_X);
    }
}
