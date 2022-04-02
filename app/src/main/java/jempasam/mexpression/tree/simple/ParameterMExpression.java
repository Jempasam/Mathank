package jempasam.mexpression.tree.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jempasam.mexpression.tree.MExpression;

public class ParameterMExpression implements MExpression {

	
	private String name;	
	
	
	public ParameterMExpression(String name) {
		super();
		this.name=name;
	}

	@Override
	public double get(Map<String, Double> parameters) {
		return parameters.get(name);
	}
	
	@Override
	public List<String> getParameters() {
		List<String> ret=new ArrayList<>();
		ret.add(name);
		return ret;
	}

	@Override
	public String getVisual() {
		return name;
	}

}
