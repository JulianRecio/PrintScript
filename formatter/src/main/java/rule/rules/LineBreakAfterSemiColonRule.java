package rule.rules;

import lexer.Token;
import rule.Rule;
import rule.RuleType;

import java.util.List;

public class LineBreakAfterSemiColonRule implements Rule {

    @Override
    public RuleType getRuleType() {
        return RuleType.GENERAL;
    }

    @Override
    public void applyRule(List<Token> tokens) {
//        tokens.get(tokens.size()-1).setValue(";\n");
    }
}
