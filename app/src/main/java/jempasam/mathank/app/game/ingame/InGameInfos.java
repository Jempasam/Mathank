package jempasam.mathank.app.game.ingame;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jempasam.mathank.app.game.init.GameEvent;
import jempasam.mathank.app.game.init.GameInfos;
import jempasam.mathank.app.game.init.GameObject;
import jempasam.mathank.app.game.init.PhysicActionSetup;
import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.item.ItemGroup;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.engine.physic.PhysicManager;
import jempasam.mathank.ihm.drawing.DrawableItemList;
import jempasam.mathank.ihm.paint.PaintBucket;

public class InGameInfos {

    protected GameInfos infos;
    protected PhysicManager physic;
    protected ItemGroup environnement;
    protected List<Item> items;
    protected List<InGameEvent> events;
    protected int gametime;
    protected DrawableItemList display;

    protected Map<String,Item> nameds;

    protected InGameInfos(GameInfos infos) {
        this.infos=infos;
        this.items=new ArrayList<>();
        this.events=new ArrayList<>();
        this.nameds=new HashMap<>();
        this.gametime=0;
    }

    public void addItem(Item item){
        items.add(item);
        environnement.add(item);
    }

    public boolean removeItem(Item item){
        environnement.remove(item);
        physic.unregister(item);
        return items.remove(item);
    }

    public PhysicManager physic(){
        return physic;
    }

    public Item getEnvironnement() {
        return environnement;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public GameInfos infos() { return infos; }

    public Item getFromId(String id){
        return nameds.get(id);
    }

    public Drawable display(){ return display; }

    public static InGameInfos startGame(GameInfos infos, int precision){
        InGameInfos game=new InGameInfos(infos);
        Vector2d view=new MutableVector2d(0,0);

        Map<Item, PaintBucket> paints=new HashMap<>();

        // Setup Items And Environnement
        for(GameObject go : infos.getObjects()){
            System.out.println(go);
            go.getBody().setPos(go.getPosition());
            game.items.add(go.getBody());
            game.nameds.put(go.getId(),go.getBody());
            paints.put(go.getBody(),go.getPaint());
            if(go.isEnvironnemental()){
                view.setX(Math.max(view.getX(),go.getBody().getSize().getX()+go.getBody().getPos().getX()));
                view.setY(Math.max(view.getY(),go.getBody().getSize().getY()+go.getBody().getPos().getY()));
            }
        }

        game.environnement=new ItemGroup(new MutableBox2d(new MutableVector2d(0,0),view));
        for(GameObject go : infos.getObjects()){
            if(go.isEnvironnemental()) {
                game.environnement.add(go.getBody());
            }
        }
        System.out.println(game.environnement);

        // Setup Physic
        game.physic=new PhysicManager(game.environnement);
        for(GameObject go : infos.getObjects()){
            for(PhysicActionSetup pa : go.getInitAction()){
                game.physic.register(go.getBody(),pa.getAction(),pa.getOnend());
            }
        }

        // Setup Display
        game.display=new DrawableItemList(paints,game.items,view,precision);

        // Setup Events
        for(GameEvent ge : infos.getEvents()) {
            game.events.add(InGameEvent.fromTimestamp(ge,0));
        }
        return game;
    }

    public void tick(){
        gametime++;
        for(InGameEvent event :events){
            if(gametime>event.getTimestamp()){
                if(!event.getEvent().tick(this)){
                    events.remove(event);
                }
                else{
                    event.setTimestamp(gametime+event.getEvent().getDelay());
                }
            }
        }
        physic.tickAll();
    }
}
