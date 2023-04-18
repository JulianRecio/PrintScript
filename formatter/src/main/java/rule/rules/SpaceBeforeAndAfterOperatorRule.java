package rule.rules;

import lexer.Token;
import lexer.TokenType;
import rule.Rule;
import rule.RuleType;

import java.util.List;

public class SpaceBeforeAndAfterOperatorRule implements Rule {
    @Override
    public RuleType getRuleType() {
        return RuleType.OPERATOR;
    }

    @Override
    public void applyRule(List<Token> tokens) {
        for (Token token:tokens){
            if (token.getType().equals(TokenType.OPERATOR)){
                token.setValue(" " + token.getValue() + " ");
            }
        }
    }
}
