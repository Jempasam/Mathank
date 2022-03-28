package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.Vector2d;

public abstract class DecoratorItem implements Item{

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
}
