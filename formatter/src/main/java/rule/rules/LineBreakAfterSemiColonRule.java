package rule.rules;

import lexer.Token;
import rule.Rule;

import java.util.List;

public class LineBreakAfterSemiColonRule implements Rule {


    @Override
    public void applyRule(List<Token> tokens) {
//        tokens.get(tokens.size()-1).setValue(";\n");
    }
}
