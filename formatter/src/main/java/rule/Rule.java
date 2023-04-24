package rule;

import lexer.Token;
import parser.node.Node;

import java.util.List;

public interface Rule {
    public void applyRule(List<Token> tokens);
}
