package rule.rules;

import lexer.Token;
import lexer.TokenType;
import rule.Rule;
import rule.RuleType;

import java.util.List;

public class LineBreakBeforePrintLnRule implements Rule {
    private final int amount;

    public LineBreakBeforePrintLnRule(int amount) {
        this.amount = amount;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.PRINT;
    }

    @Override
    public void applyRule(List<Token> tokens) {
        if (amount < 0 || amount > 2) throw new RuntimeException();
        StringBuilder str = new StringBuilder();
        str.append("\n".repeat(amount));
        str.append("PrintLn");

        for (Token token:tokens){
            if (token.getType().equals(TokenType.PRINT)){
                token.setValue(str.toString());
            }
        }
    }
}
