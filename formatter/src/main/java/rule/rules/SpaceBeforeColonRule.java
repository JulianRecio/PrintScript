package rule.rules;

import java.util.List;
import rule.Rule;
import token.Token;
import token.TokenType;

public class SpaceBeforeColonRule implements Rule {

  @Override
  public void applyRule(List<Token> tokens) {
    for (Token token : tokens) {
      if (token.getType().equals(TokenType.ALLOCATOR)) {
        token.setValue(" " + token.getValue());
      }
    }
  }
}
