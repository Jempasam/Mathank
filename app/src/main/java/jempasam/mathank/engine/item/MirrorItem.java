package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;

@Loadable
public class MirrorItem extends DecoratorItem{
    public MirrorItem(Item decorated){
        super(decorated);
    }

    private MirrorItem(){super(null);}

    @Override
    public boolean doCollide(Vector2d point) {
        return super.doCollide(new MutableVector2d(getPos().getX()*2+getSize().getX()-point.getX(),point.getY()));
    }
}
