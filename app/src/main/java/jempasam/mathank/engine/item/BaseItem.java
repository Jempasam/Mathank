package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;

public abstract class BaseItem implements Item{

    protected Box2d box;

    protected  BaseItem(Box2d box){
        this.box=box;
    }

    @Override
    public Vector2d getPos() { return box.getPos(); }

    @Override
    public void setPos(Vector2d n) { box.setPos(new MutableVector2d(n)); }

    @Override
    public Vector2d getSize() { return box.getSize(); }

    @Override
    public void setSize(Vector2d n) {
        box.setSize(new MutableVector2d(n));
    }

    @Override
    public Box2d getBox() {
        return box;
    }

    @Override
    public void setBox(Box2d box) {
        this.box=new MutableBox2d(box);
    }
}
