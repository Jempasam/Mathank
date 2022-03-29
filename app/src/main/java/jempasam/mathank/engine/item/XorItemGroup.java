package jempasam.mathank.engine.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class XorItemGroup extends BaseItemGroup{

    public XorItemGroup(Box2d box) {
        super(box);
    }

    private XorItemGroup(){super();}

    @Override
    public boolean doChildsCollide(Vector2d point){
        boolean ret=false;
        for(Item it: items){
            if(/*it.getBox().containPoint(point) && */it.doCollide(point)){
                if(ret)return false;
                else ret=true;
            }
        }
        return ret;
    }
}
