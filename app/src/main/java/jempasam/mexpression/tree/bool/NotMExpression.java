package jempasam.mexpression.tree.bool;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;
import jempasam.mexpression.tree.unary.UnaryMExpression;

public class NotMExpression extends UnaryMExpression {

	public NotMExpression(MExpression a) {
		super(a, "!","");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return a.get(parameters)==0d ? 1d : 0d;
	}
	
}
