package jempasam.mathank.engine.item;

import java.util.HashMap;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mexpression.tree.MExpression;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class MExpressionItem extends BaseItem {

    private MExpression expression;
    private HashMap<String,Double> parameters;

    public MExpressionItem(Box2d box, MExpression expression) {
        super(box);
        this.expression=expression;
        this.parameters=new HashMap<>();
        for(String n : expression.getParameters()){
            parameters.put(n,0d);
        }
    }

    @Override
    public boolean doCollide(Vector2d point) {
        parameters.put("X",Double.valueOf(point.getX()));
        parameters.put("Y",Double.valueOf(point.getY()));
        return expression.get(parameters)>0d;
    }

    private MExpressionItem(){super(null);}
    @LoadableParameter
    public void expression(MExpression expression) {
        this.expression=expression;
        this.parameters=new HashMap<>();
        for(String n : expression.getParameters()){
            parameters.put(n,0d);
        }
    }
}
