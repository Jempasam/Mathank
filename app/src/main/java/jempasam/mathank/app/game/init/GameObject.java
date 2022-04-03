package jempasam.mathank.app.game.init;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class GameObject {

    @LoadableParameter private Item body;
    private List<PhysicActionSetup> init_action;
    @LoadableParameter private PaintBucket paint;
    @LoadableParameter private boolean environnemental;
    @LoadableParameter private Vector2d position;
    @LoadableParameter private String id;

    public GameObject(Item body, Vector2d position, PaintBucket paint, List<PhysicActionSetup> init_action, boolean environnemental) {
        this.body = body;
        this.init_action = init_action;
        this.paint=paint;
        this.environnemental = environnemental;
        this.position=position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public Item getBody() {
        return body;
    }

    public void setBody(Item body) {
        this.body = body;
    }

    public List<PhysicActionSetup> getInitAction() {
        return init_action;
    }

    public void setInitAction(List<PhysicActionSetup> init_action) {
        this.init_action = init_action;
    }

    public boolean isEnvironnemental() {
        return environnemental;
    }

    public void setEnvironnemental(boolean environnemental) {
        this.environnemental = environnemental;
    }

    public PaintBucket getPaint() {
        return paint;
    }

    public void setPaint(PaintBucket paint) {
        this.paint = paint;
    }


    /* LOADABLE */
    private GameObject(){
        environnemental=false;
        init_action=new ArrayList<>();
    }

    @LoadableParameter
    private void physic(PhysicActionSetup physic){
        init_action.add(physic);
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "body=" + body +
                ", init_action=" + init_action +
                ", paint=" + paint +
                ", environnemental=" + environnemental +
                ", position=" + position +
                ", id='" + id + '\'' +
                '}';
    }
}
