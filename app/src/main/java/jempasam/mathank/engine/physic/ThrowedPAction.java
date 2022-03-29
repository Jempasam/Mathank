package jempasam.mathank.engine.physic;

import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;

public class ThrowedPAction implements PhysicAction{

    private int xspeed;
    private int yspeed;
    private int gravity;
    private float friction;

    public ThrowedPAction(int xspeed, int yspeed, int gravity, float friction) {
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.gravity=gravity;
        this.friction=friction;
    }

    @Override
    public boolean simulate(Item target, Item environment) {
        Vector2d nextcenter=target.getBox().center();
        nextcenter.setX(nextcenter.getX()+xspeed);
        nextcenter.setY(nextcenter.getY()+yspeed);

        yspeed=Math.min(gravity,yspeed+Math.max(gravity/10,1));

        if(environment.doCollide(nextcenter)){
            yspeed=-yspeed;
            return false;
        }
        else{
            target.getPos().setX(target.getPos().getX()+xspeed);
            target.getPos().setY(target.getPos().getY()+yspeed);
            return true;
        }
    }
}
