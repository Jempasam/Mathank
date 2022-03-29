package jempasam.mathank.app.game.init;

import android.graphics.Paint;

import java.util.List;

import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.ihm.paint.PaintBucket;

public class GameObject {

    private Item body;
    private List<PhysicActionSetup> init_action;
    private PaintBucket paint;
    private boolean environnemental;

    public GameObject(Item body, PaintBucket paint, List<PhysicActionSetup> init_action, boolean environnemental) {
        this.body = body;
        this.init_action = init_action;
        this.paint=paint;
        this.environnemental = environnemental;
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
}
