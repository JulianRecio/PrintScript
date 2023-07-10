package formatter.rule.rules;

import formatter.rule.Rule;
import token.Token;
import token.TokenType;

import java.util.List;

public class QuotationMarkInStringRule implements Rule {
    @Override
    public void applyRule(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.getType().equals(TokenType.STRING_VALUE)) {
                if (token.getValue().charAt(0) != '\"') token.setValue("\"" + token.getValue() + "\"");
            }
        }
    }
}
