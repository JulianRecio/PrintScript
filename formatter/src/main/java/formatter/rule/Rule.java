package formatter.rule;

import java.util.List;
import token.Token;

public interface Rule {
  public void applyRule(List<Token> tokens);
}
