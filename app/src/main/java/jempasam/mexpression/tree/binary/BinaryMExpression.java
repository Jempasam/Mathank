package jempasam.mexpression.tree.binary;

import java.util.List;

import jempasam.mexpression.tree.MExpression;

public abstract class BinaryMExpression implements MExpression{

	
	protected MExpression a;
	protected MExpression b;
	private String visual;
	
	
	public BinaryMExpression(MExpression a, MExpression b, String sign) {
		super();
		this.a = a;
		this.b = b;
		this.visual="("+a.getVisual()+sign+b.getVisual()+")";
	}
	
	@Override
	public List<String> getParameters() {
		List<String> ret=a.getParameters();
		ret.addAll(b.getParameters());
		return ret;
	}
	
	@Override
	public String getVisual() {
		return visual;
	}
}
