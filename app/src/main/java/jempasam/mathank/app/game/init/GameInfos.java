package jempasam.mathank.app.game.init;

import java.util.ArrayList;
import java.util.List;

public class GameInfos {

    private List<GameObject> objects;
    private List<GameEvent> events;
    private String title;

    public GameInfos(String title){
        this.title=title;
        objects = new ArrayList<>();
        events = new ArrayList<>();
    }

    public void add(GameObject obj){
        objects.add(obj);
    }

    public void add(GameEvent event){
        events.add(event);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    public String getTitle() {
        return title;
    }


}
