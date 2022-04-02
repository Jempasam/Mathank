package jempasam.mexpression.tree.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class NumberMExpression implements MExpression {
	
	
	private double value;
	private String visual;
	
	
	public NumberMExpression(double value) {
		super();
		this.value = value;
		this.visual = Double.toString(value);
	}


	@Override
	public double get(Map<String, Double> parameters) {
		return value;
	}
	
	@Override
	public List<String> getParameters() {
		return new ArrayList<>();
	}
	
	@Override
	public String getVisual() {
		return visual;
	}
	
}
