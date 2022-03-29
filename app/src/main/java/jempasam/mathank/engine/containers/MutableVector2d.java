package jempasam.mathank.engine.containers;

import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

public class MutableVector2d implements Vector2d {

    public int x=0;
    public int y=0;

    public MutableVector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MutableVector2d(Vector2d cloned) {
        this.x = cloned.getX();
        this.y = cloned.getY();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x=x;
    }

    @Override
    public void setY(int y) {
        this.y=y;
    }


}
