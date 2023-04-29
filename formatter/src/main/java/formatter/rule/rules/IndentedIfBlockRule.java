package formatter.rule.rules;

import formatter.rule.Rule;
import java.util.List;
import token.Token;
import token.TokenType;

public class IndentedIfBlockRule implements Rule {
  private final int amount;

  public IndentedIfBlockRule(int amount) {
    this.amount = amount;
  }

  @Override
  public void applyRule(List<Token> tokens) {
    boolean applyIndentation = false;

    for (Token token : tokens) {
      if (applyIndentation) {
        StringBuilder str = new StringBuilder();
        str.append(" ".repeat(amount));
        str.append(token.getValue());
        token.setValue(str.toString());
        applyIndentation = false;
      }
      if (token.getType().equals(TokenType.LEFT_BRACKET)) {
        applyIndentation = true;
      }
    }
  }
}
