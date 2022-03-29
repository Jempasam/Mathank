package jempasam.mathank.engine.physic;

import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;

public class SlideGravityPAction implements  PhysicAction{

    private int fallspeed;

    public SlideGravityPAction(int fallspeed){
        this.fallspeed=fallspeed;
    }

    @Override
    public boolean simulate(Item target, Item environment) {
        Vector2d footpos=new MutableVector2d(target.getPos().getX()+target.getSize().getX()/2, target.getPos().getY()+target.getSize().getY()+fallspeed);
        if(!environment.doCollide(footpos)){
            target.getPos().setY(target.getPos().getY()+fallspeed);
        }
        else{
            Vector2d leftfootpos=new MutableVector2d(target.getPos().getX()+target.getSize().getX()/4, target.getPos().getY()+target.getSize().getY()+fallspeed);
            Vector2d rightfootpos=new MutableVector2d(target.getPos().getX()+target.getSize().getX()*3/4, target.getPos().getY()+target.getSize().getY()+fallspeed);
            boolean goleft=!environment.doCollide(leftfootpos);
            boolean goright=!environment.doCollide(rightfootpos);
            if(goleft && !goright){
                target.getPos().setX(target.getPos().getX()-fallspeed);
            }
            else if(goright && !goleft){
                target.getPos().setX(target.getPos().getX()+fallspeed);
            }
        }
        return true;
    }

    public int getFallspeed() {
        return fallspeed;
    }

    public void setFallspeed(int fallspeed) {
        this.fallspeed = fallspeed;
    }
}
