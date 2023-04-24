package rule.rules;

import lexer.Token;
import lexer.TokenType;
import rule.Rule;

import java.util.List;

public class IndentedIfBlockRule implements Rule {
    private final int amount;

    public IndentedIfBlockRule(int amount) {
        this.amount = amount;
    }


    @Override
    public void applyRule(List<Token> tokens) {
        boolean applyIndentation = false;

        for (Token token:tokens){
            if (applyIndentation){
                StringBuilder str = new StringBuilder();
                str.append(" ".repeat(amount));
                str.append(token.getValue());
                token.setValue(str.toString());
                applyIndentation = false;
            }
            if (token.getType().equals(TokenType.LEFT_BRACKET)){
                applyIndentation = true;
            }
        }
    }
}
