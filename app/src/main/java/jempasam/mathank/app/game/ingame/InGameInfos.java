package jempasam.mathank.app.game.ingame;

import java.util.ArrayList;
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
    protected Item environnement;
    protected List<Item> items;
    protected List<InGameEvent> events;
    protected int gametime;
    protected DrawableItemList display;

    protected InGameInfos(GameInfos infos) {
        this.infos=infos;
        this.items=new ArrayList<>();
        this.events=new ArrayList<>();
        this.gametime=0;
    }

    public static void startGame(GameInfos infos, int precision){
        InGameInfos game=new InGameInfos(infos);
        Vector2d view=new MutableVector2d(0,0);

        Map<Item, PaintBucket> paints=new HashMap<>();

        // Setup Items And Environnement
        for(GameObject go : infos.getObjects()){
            game.items.add(go.getBody());
            paints.put(go.getBody(),go.getPaint());
            if(go.isEnvironnemental()){
                view.setX(Math.max(view.getX(),go.getBody().getSize().getX()+go.getBody().getPos().getX()));
                view.setY(Math.max(view.getY(),go.getBody().getSize().getY()+go.getBody().getPos().getY()));
            }
        }

        ItemGroup env=new ItemGroup(new MutableBox2d(new MutableVector2d(0,0),view));
        for(GameObject go : infos.getObjects()){
            if(go.isEnvironnemental()) {
                env.add(go.getBody());
            }
        }
        game.environnement=env;

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
    }
}
