package rule.rules;

import lexer.Token;
import rule.Rule;

import java.util.List;

public class LineBreakBeforePrintLnRule implements Rule {
    private final int amount;

    public LineBreakBeforePrintLnRule(int amount) {
        this.amount = amount;
    }
    @Override
    public void applyRule(List<Token> tokens) {

    }
}
