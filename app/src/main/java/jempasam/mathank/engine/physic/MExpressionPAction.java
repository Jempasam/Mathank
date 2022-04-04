package jempasam.mathank.engine.physic;

import java.util.HashMap;

import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mexpression.tree.MExpression;

public class MExpressionPAction implements PhysicAction{

    private MExpression expression;
    private HashMap<String,Double> parameters;
    private int initialx;
    private int initialy;
    private int direction;
    private int speed;

    public MExpressionPAction(Item target, MExpression expression, int speed, int direction) {
        this.expression=expression;
        this.parameters=new HashMap<>();
        this.direction=direction;
        this.speed=speed;
        for(String str : expression.getParameters()){
            parameters.put(str,0d);
        }
        this.initialx=target.getPos().getX();
        parameters.put("X",0d);
        this.initialy=target.getPos().getY()-(int)(expression.get(parameters));
    }

    @Override
    public boolean simulate(Item target, Item environment) {
        target.getPos().setX(target.getPos().getX()+speed*direction);
        parameters.put("X",Double.valueOf((target.getPos().getX()-initialx)*direction));
        int ny=(int)(expression.get(parameters))+initialy;
        target.getPos().setY(ny);
        Vector2d center=target.getBox().center();
        if(environment.doCollide(center) || !environment.getBox().containPoint(center)){
            return false;
        }
        return true;
    }
}
