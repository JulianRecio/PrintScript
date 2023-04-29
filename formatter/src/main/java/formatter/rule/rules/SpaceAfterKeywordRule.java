package formatter.rule.rules;

import formatter.rule.Rule;
import java.util.List;
import token.Token;
import token.TokenType;

public class SpaceAfterKeywordRule implements Rule {

  @Override
  public void applyRule(List<Token> tokens) {
    for (Token token : tokens) {
      if (token.getType().equals(TokenType.KEYWORD)) {
        token.setValue(token.getValue() + " ");
      }
    }
  }
}
