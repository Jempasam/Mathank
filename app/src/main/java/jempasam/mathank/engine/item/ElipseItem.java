package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;

public class ElipseItem extends BaseItem {

    public ElipseItem(Box2d box) {
        super(box);
    }

    @Override
    public boolean doCollide(Vector2d point) {

        int d=getSize().getX()/2-1;
        Vector2d center=new MutableVector2d(getPos().getX()+getSize().getX()/2, getPos().getY()+getSize().getY()/2);

        return center.distance(point)<=d;
    }
}
