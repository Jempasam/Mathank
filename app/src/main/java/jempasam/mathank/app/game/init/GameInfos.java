package jempasam.mathank.app.game.init;

import java.util.ArrayList;
import java.util.List;

import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class GameInfos {

    private List<GameObject> objects;
    private List<GameEvent> events;
    private List<String> keys;
    @LoadableParameter private String title;

    public GameInfos(String title){
        this();
        this.title=title;
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

    public List<String> getKeys() { return keys; }

    public String getTitle() {
        return title;
    }


    /* LOADABLE */
    private GameInfos(){
        objects = new ArrayList<>();
        events = new ArrayList<>();
        keys=new ArrayList<>();
    }

    @LoadableParameter
    private void object(GameObject obj){
        objects.add(obj);
    }

    @LoadableParameter
    private void event(GameEvent event){
        events.add(event);
    }

    @LoadableParameter
    private void key(String k){
        keys.add(k);
    }
}
