package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;

@Loadable
public class SquareItem extends BaseItem {

    public SquareItem(Box2d box) {
        super(box);
    }

    private SquareItem(){super(null);}

    @Override
    public boolean doCollide(Vector2d point) {
        return getBox().containPoint(point);
    }
}
