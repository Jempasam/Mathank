package jempasam.mexpression.tree.serializer;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import jempasam.mexpression.tree.builder.MExpressionTerm;

public interface MExpressionSerializer {
	public List<MExpressionTerm> serialize(Iterator<String> words)  throws MExpressionSerializerException;
	
	public class MExpressionSerializerException extends Exception{
		private String token;

		public MExpressionSerializerException(String token) {
			super("Invalid Token "+token+".");
			this.token = token;
		}

		public String getToken() {
			return token;
		}
		
	}
	
	public static PatternListMExpressionSerializer createBaseMExpressionSerializer() {
		PatternListMExpressionSerializer ret=new PatternListMExpressionSerializer();
		ret.register(Pattern.compile("\\+"), MExpressionTerm.ADD);
		ret.register(Pattern.compile("\\-"), MExpressionTerm.MINUS);
		ret.register(Pattern.compile("\\*"), MExpressionTerm.MULTIPLY);
		ret.register(Pattern.compile("\\/"), MExpressionTerm.DIVIDE);
		ret.register(Pattern.compile("\\^"), MExpressionTerm.POWER);
		
		ret.register(Pattern.compile("abs"), MExpressionTerm.ABSOLUTE);
		ret.register(Pattern.compile("sqrt"), MExpressionTerm.SQRT);
		
		ret.register(Pattern.compile("\\("), MExpressionTerm.OPEN);
		ret.register(Pattern.compile("\\)"), MExpressionTerm.CLOSE);
		
		ret.register(Pattern.compile("\\>"), MExpressionTerm.GREATER_THAN);
		ret.register(Pattern.compile("\\<"), MExpressionTerm.LOWER_THAN);
		ret.register(Pattern.compile("\\="), MExpressionTerm.EQUALS);
		
		ret.register(Pattern.compile("and"), MExpressionTerm.AND);
		ret.register(Pattern.compile("or"), MExpressionTerm.OR);
		
		ret.register(Pattern.compile("!"), MExpressionTerm.NOT);
		
		ret.register(Pattern.compile("\\-?[1-9]+.?[1-9]*"), (str)->MExpressionTerm.of(Double.parseDouble(str)));
		ret.register(Pattern.compile("[A-Z]"), (str)->MExpressionTerm.of(str));
		
		return ret;
	}
}
