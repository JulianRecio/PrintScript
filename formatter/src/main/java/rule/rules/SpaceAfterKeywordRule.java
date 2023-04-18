package rule.rules;

import lexer.Token;
import lexer.TokenType;
import rule.Rule;
import rule.RuleType;

import java.util.List;

public class SpaceAfterKeywordRule implements Rule {
    @Override
    public RuleType getRuleType() {
        return RuleType.ASSIGNATION;
    }

    @Override
    public void applyRule(List<Token> tokens) {
        for (Token token:tokens){
            if (token.getType().equals(TokenType.KEYWORD)){
                token.setValue(token.getValue() + " ");
            }
        }
    }
}
