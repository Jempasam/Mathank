package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

public abstract class DecoratorItem implements Item{

    @LoadableParameter
    protected  Item item;

    public DecoratorItem(Item item) {
        this.item=item;
    }

    @Override
    public Vector2d getPos() {
        return item.getPos();
    }

    @Override
    public void setPos(Vector2d n) {
        item.setPos(n);
    }

    @Override
    public Vector2d getSize() {
        return item.getSize();
    }

    @Override
    public void setSize(Vector2d n) {
        item.setSize(n);
    }

    @Override
    public Box2d getBox() {
        return item.getBox();
    }

    @Override
    public void setBox(Box2d box) {
        item.setBox(box);
    }

    @Override
    public boolean doCollide(Vector2d point) {
        return item.doCollide(point);
    }

    public DecoratorItem clone() throws CloneNotSupportedException {
        DecoratorItem ret=(DecoratorItem)super.clone();
        ret.item=item.clone();
        return ret;
    }

    @Override
    public String toString() {
        return "Mirror{"+item+"}";
    }
}
