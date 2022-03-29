package jempasam.mathank.app.game.init;

public abstract class GameEvent {

    private int delay;

    public abstract boolean tick();

    public int getDelay() {
        return delay;
    }
}
