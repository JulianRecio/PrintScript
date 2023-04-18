package rule;

import lexer.Token;
import parser.node.Node;

import java.util.List;

public interface Rule {
    public RuleType getRuleType();
    public void applyRule(List<Token> tokens);
    public void applyRule(Node node);
}
