package jempasam.mexpression.tree.bool;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;
import jempasam.mexpression.tree.binary.BinaryMExpression;

public class AndMExpression extends BinaryMExpression {

	public AndMExpression(MExpression a, MExpression b) {
		super(a, b, " and ");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return a.get(parameters)*b.get(parameters);
	}
	
}
