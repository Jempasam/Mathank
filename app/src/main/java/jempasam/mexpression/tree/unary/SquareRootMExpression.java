package jempasam.mexpression.tree.unary;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class SquareRootMExpression extends UnaryMExpression {

	public SquareRootMExpression(MExpression a) {
		super(a, "sqrt(",")");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return Math.sqrt(a.get(parameters));
	}
	
}
