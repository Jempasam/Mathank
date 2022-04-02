package jempasam.mexpression.tree.binary;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class PowerMExpression extends BinaryMExpression {

	public PowerMExpression(MExpression a, MExpression b) {
		super(a, b, "^");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return Math.pow(a.get(parameters),b.get(parameters));
	}
	
}
