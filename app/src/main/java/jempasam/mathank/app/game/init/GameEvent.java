package jempasam.mathank.app.game.init;

import jempasam.mathank.app.game.ingame.InGameInfos;

public abstract class GameEvent {

    private int delay;

    public abstract boolean tick(InGameInfos infos);

    public int getDelay() {
        return delay;
    }
}
