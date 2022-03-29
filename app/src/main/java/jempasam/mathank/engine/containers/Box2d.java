package jempasam.mathank.engine.containers;

import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.swj.objectmanager.loader.tags.Loadable;

@Loadable
public interface Box2d{

    Vector2d getPos();
    Vector2d getSize();
    void setPos(Vector2d v);
    void setSize(Vector2d v);

    default Vector2d cornerLT(){
        return getPos();
    }

    default Vector2d cornerRB(){
        Vector2d pos=getPos();
        Vector2d size=getSize();
        return new MutableVector2d(pos.getX()+size.getX(),pos.getY()+size.getY());
    }

    default boolean containPoint(Vector2d point){
        Vector2d posLT=cornerLT();
        Vector2d posRB=cornerRB();
        return posLT.getX()<=point.getX() && point.getX()<posRB.getX() && posLT.getY()<=point.getY() && point.getY()<posRB.getY();
    }
}
