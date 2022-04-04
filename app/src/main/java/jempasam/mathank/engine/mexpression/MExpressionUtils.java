package jempasam.mathank.engine.mexpression;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import jempasam.mexpression.tree.MExpression;
import jempasam.mexpression.tree.builder.MExpressionBuilder;
import jempasam.mexpression.tree.builder.MExpressionTerm;
import jempasam.mexpression.tree.serializer.MExpressionSerializer;
import jempasam.swj.textanalyzis.tokenizer.Tokenizer;
import jempasam.swj.textanalyzis.tokenizer.impl.InputStreamSimpleTokenizer;

public class MExpressionUtils {
    public static MExpression parse(String str) throws MExpressionBuilder.MExpressionBuilderException, MExpressionSerializer.MExpressionSerializerException {
        // STR -> TOKENS
        Tokenizer tokenizer = new InputStreamSimpleTokenizer(new ByteArrayInputStream(str.getBytes()), " ", "+-*/^!|=><()", "");
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasNext()) tokens.add(tokenizer.next());

        // TOKENS -> TERMS
        MExpressionSerializer serializer = MExpressionSerializer.createBaseMExpressionSerializer();
        List<MExpressionTerm> terms = serializer.serialize(tokens.iterator());

        // TERMS -> EQUATION
        MExpressionBuilder builder=new MExpressionBuilder();
        MExpression expression=builder.compile(terms);

        return expression;
    }
}
