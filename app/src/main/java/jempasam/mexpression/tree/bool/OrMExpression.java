package jempasam.mexpression.tree.bool;

import java.util.Map;

import jempasam.mexpression.tree.MExpression;
import jempasam.mexpression.tree.binary.BinaryMExpression;

public class OrMExpression extends BinaryMExpression {

	public OrMExpression(MExpression a, MExpression b) {
		super(a, b, " or ");
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return a.get(parameters)+b.get(parameters)>0d ? 1d : 0d;
	}
	
}
