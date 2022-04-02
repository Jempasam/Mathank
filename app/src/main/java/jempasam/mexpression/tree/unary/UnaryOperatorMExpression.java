package jempasam.mexpression.tree.unary;

import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import jempasam.mexpression.tree.MExpression;

public class UnaryOperatorMExpression extends UnaryMExpression {

	private DoubleUnaryOperator operator;
	
	public UnaryOperatorMExpression(MExpression a, DoubleUnaryOperator operator) {
		super(a, operator.getClass().getSimpleName()+"(",")");
		this.operator=operator;
	}
	
	@Override
	public double get(Map<String, Double> parameters) {
		return operator.applyAsDouble(a.get(parameters));
	}
	
}