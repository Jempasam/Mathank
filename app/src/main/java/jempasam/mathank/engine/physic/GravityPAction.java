package jempasam.mathank.engine.physic;

import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class GravityPAction implements  PhysicAction{

    @LoadableParameter
    private int fallspeed;

    public GravityPAction(int fallspeed){
        this.fallspeed=fallspeed;
    }

    @Override
    public boolean simulate(Item target, Item environment) {
        Vector2d footpos=new MutableVector2d(target.getPos().getX()+target.getSize().getX()/2, target.getPos().getY()+target.getSize().getY()+fallspeed);
        if(!environment.doCollide(footpos)){
            target.getPos().setY(target.getPos().getY()+fallspeed);
        }
        return true;
    }

    public int getFallspeed() {
        return fallspeed;
    }

    public void setFallspeed(int fallspeed) {
        this.fallspeed = fallspeed;
    }

    /* LOADABLE */
    private GravityPAction(){ }
}
