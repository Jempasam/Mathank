package jempasam.mexpression.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jempasam.mexpression.tree.builder.MExpressionBuilder;
import jempasam.mexpression.tree.builder.MExpressionBuilder.MExpressionBuilderException;
import jempasam.mexpression.tree.builder.MExpressionTerm;
import jempasam.mexpression.tree.serializer.MExpressionSerializer;
import jempasam.mexpression.tree.serializer.MExpressionSerializer.MExpressionSerializerException;

public interface MExpression {
	
	double get(Map<String, Double> parameters);
	String getVisual();
	List<String> getParameters();
	
	public static void main(String[] args) {
		try {
			String equation="2*(X+5)";
			System.out.println("String: "+equation);
			
			// Tokenizer from STR to TOKENS
			/*Tokenizer tokenizer=new InputStreamSimpleTokenizer(new ByteArrayInputStream(equation.getBytes()), " ", "+-/*()^", "");
			List<String> s=new ArrayList<>();
			while(tokenizer.hasNext())s.add(tokenizer.next());
			String str[]=s.toArray(new String[0]);*/
			String str[]=new String[] {"2","*","(","X","+","5",")",">","20","or","1"};
			
			System.out.println("Tokens: "+Arrays.toString(str));
			
			// From TOKENS to LIST
			MExpressionSerializer serializer=MExpressionSerializer.createBaseMExpressionSerializer();
			List<MExpressionTerm> terms=serializer.serialize(Arrays.stream(str).iterator());
			System.out.println("Terms: "+terms);
			
			// From LIST to EQUATION
			MExpressionBuilder builder=new MExpressionBuilder();
			MExpression mexpression=builder.compile(terms);
			System.out.println("Equation: "+mexpression.getVisual());
			
			// Use EQUATION
			Map<String,Double> parameters=new HashMap<>();
			System.out.println("Parameters : "+mexpression.getParameters());
			
			parameters.put("X", 1d);
			System.out.println("Parameters : "+parameters+" -> "+mexpression.get(parameters));
			
			parameters.put("X", 10d);
			System.out.println("Parameters : "+parameters+" -> "+mexpression.get(parameters));
			
			parameters.put("X", 15d);
			System.out.println("Parameters : "+parameters+" -> "+mexpression.get(parameters));
			
			parameters.put("X", 2d);
			System.out.println("Parameters : "+parameters+" -> "+mexpression.get(parameters));
			
		}catch(MExpressionBuilderException e) {
			e.printStackTrace();
		} catch (MExpressionSerializerException e1) {
			e1.printStackTrace();
		}
	}
	
}
