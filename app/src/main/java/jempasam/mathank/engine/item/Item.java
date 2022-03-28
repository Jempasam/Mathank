package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.Vector2d;

public interface Item {

    Vector2d getPos();
    void setPos(Vector2d n);

    Vector2d getSize();
    void setSize(Vector2d n);

    Box2d getBox();
    void setBox(Box2d box);

    boolean doCollide(Vector2d point);
}
