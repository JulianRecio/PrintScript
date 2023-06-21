package parser.nodeParser;

import ast.expr.Expression;
import ast.node.AssignationNode;
import ast.node.Node;
import ast.obj.AttributeObject;
import com.google.common.collect.PeekingIterator;
import parser.NodeParser;
import parser.expressionParser.ExprParser;
import token.Token;
import token.TokenType;

public class AssignationParser implements NodeParser {

  private final PeekingIterator<Token> tokenIterator;

  public AssignationParser(PeekingIterator<Token> tokenIterator) {
    this.tokenIterator = tokenIterator;
  }

  @Override
  public Node parseNode() {
    String variableName = tokenIterator.next().getValue();
    if (tokenIterator.next().getType() == TokenType.EQUAL) {
      Token token = tokenIterator.next();
      Expression<AttributeObject> right = new ExprParser(tokenIterator).parseExpression(token);
      if (tokenIterator.next().getType() == TokenType.END) {
        return new AssignationNode(variableName, right);
      } else throw new RuntimeException("; was expected but not found");
    } else throw new RuntimeException("= was expected but not found");
  }
}
