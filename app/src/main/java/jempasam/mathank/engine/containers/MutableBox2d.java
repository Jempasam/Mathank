package jempasam.mathank.engine.containers;

import android.graphics.Rect;

import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class MutableBox2d implements Box2d{

    @LoadableParameter
    private Vector2d position;

    @LoadableParameter
    private Vector2d size;

    public MutableBox2d(int posx, int posy, int sizex, int sizey){
        position=new MutableVector2d(posx,posy);
        size=new MutableVector2d(sizex,sizey);
    }

    public MutableBox2d(Vector2d pos, Vector2d size){
        position=pos;
        size=size;
    }

    public MutableBox2d(Box2d copied){
        this(copied.getPos(),copied.getSize());
    }

    @Override
    public Vector2d getPos() {
        return position;
    }

    @Override
    public Vector2d getSize() {
        return size;
    }

    @Override
    public void setPos(Vector2d v) {
        position=v;
    }

    @Override
    public void setSize(Vector2d v) {
        size=v;
    }

}
