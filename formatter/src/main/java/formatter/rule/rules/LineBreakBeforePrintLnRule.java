package formatter.rule.rules;

import formatter.rule.Rule;
import java.util.List;
import token.Token;
import token.TokenType;

public class LineBreakBeforePrintLnRule implements Rule {
  private final int amount;

  public LineBreakBeforePrintLnRule(int amount) {
    this.amount = amount;
  }

  @Override
  public void applyRule(List<Token> tokens) {
    if (amount < 0 || amount > 2) throw new RuntimeException();
    StringBuilder str = new StringBuilder();
    str.append("\n".repeat(amount));
    str.append("printLn");

    for (Token token : tokens) {
      if (token.getType().equals(TokenType.PRINT)) {
        token.setValue(str.toString());
      }
    }
  }
}
