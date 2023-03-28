package rule;

import lexer.Token;

import java.util.List;

public interface Rule {
    public void applyRule(List<Token> tokens);
}
