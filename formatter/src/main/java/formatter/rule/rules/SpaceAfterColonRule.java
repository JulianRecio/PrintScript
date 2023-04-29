package formatter.rule.rules;

import formatter.rule.Rule;
import java.util.List;
import token.Token;
import token.TokenType;

public class SpaceAfterColonRule implements Rule {

  @Override
  public void applyRule(List<Token> tokens) {
    for (Token token : tokens) {
      if (token.getType().equals(TokenType.ALLOCATOR)) {
        token.setValue(token.getValue() + " ");
      }
    }
  }
}
