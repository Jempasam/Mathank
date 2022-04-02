package jempasam.mexpression.tree.unary;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class AbsoluteMExpression extends UnaryMExpression {

	public AbsoluteMExpression(MExpression a) {
		super(a, "|","|");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return Math.abs(a.get(parameters));
	}
	
}
