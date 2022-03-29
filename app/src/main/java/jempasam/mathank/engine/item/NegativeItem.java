package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;

@Loadable
public class NegativeItem extends DecoratorItem{

    public NegativeItem(Item item) {
        super(item);
    }

    private NegativeItem(){super(null);}

    @Override
    public boolean doCollide(Vector2d point) {
        return !item.doCollide(point);
    }
}
