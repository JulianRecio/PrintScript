package rule.rules;

import lexer.Token;
import lexer.TokenType;
import rule.Rule;

import java.util.List;

public class SpaceAfterColonRule implements Rule {
    @Override
    public void applyRule(List<Token> tokens) {
        for (Token token:tokens){
            if (token.getType().equals(TokenType.ALLOCATOR)){
                token.setValue(": ");
            }
        }
    }
}
