package jempasam.mexpression.tree.binary;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class MultiplyMExpression extends BinaryMExpression {

	public MultiplyMExpression(MExpression a, MExpression b) {
		super(a, b, "×");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return a.get(parameters)*b.get(parameters);
	}
	
}
