package jempasam.mexpression.tree.unary;

import java.util.List;

import jempasam.mexpression.tree.MExpression;

public abstract class UnaryMExpression implements MExpression{

	
	protected MExpression a;
	private String visual;
	
	
	public UnaryMExpression(MExpression a, String before, String after) {
		super();
		this.a = a;
		this.visual=before+a.getVisual()+after;
	}
	
	@Override
	public List<String> getParameters() {
		return a.getParameters();
	}
	
	@Override
	public String getVisual() {
		return visual;
	}
}
