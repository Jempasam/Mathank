package jempasam.mathank.app.game.ingame;

import jempasam.mathank.app.game.init.GameEvent;

public class InGameEvent {

    private GameEvent event;
    private int timestamp;

    public InGameEvent(GameEvent event, int timestamp) {
        this.event = event;
        this.timestamp = timestamp;
    }

    public static InGameEvent fromTimestamp(GameEvent event, int currentTime){
        return new InGameEvent(event,event.getDelay()+currentTime);
    }

    public GameEvent getEvent() {
        return event;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
