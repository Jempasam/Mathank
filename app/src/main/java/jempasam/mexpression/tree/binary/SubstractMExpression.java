package jempasam.mexpression.tree.binary;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class SubstractMExpression extends BinaryMExpression {

	public SubstractMExpression(MExpression a, MExpression b) {
		super(a, b, "-");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return a.get(parameters)-b.get(parameters);
	}
	
}
