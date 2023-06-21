package parser.nodeParser;

import ast.expr.Expression;
import ast.node.Node;
import ast.node.PrintNode;
import ast.obj.AttributeObject;
import com.google.common.collect.PeekingIterator;
import parser.NodeParser;
import parser.expressionParser.ExprParser;
import token.Token;
import token.TokenType;

public class PrintParser implements NodeParser {

  private final PeekingIterator<Token> tokenIterator;

  public PrintParser(PeekingIterator<Token> tokenIterator) {
    this.tokenIterator = tokenIterator;
  }

  @Override
  public Node parseNode() {
    if (tokenIterator.next().getType() == TokenType.LEFT_PARENTHESIS) {
      Token token = tokenIterator.next();
      Expression<AttributeObject> expression = new ExprParser(tokenIterator).parseExpression(token);
      if (tokenIterator.peek().getType() == TokenType.RIGHT_PARENTHESIS) {
        tokenIterator.remove();
        if (tokenIterator.next().getType() == TokenType.END) {
          return new PrintNode(expression);
        } else throw new RuntimeException("; was expected but not found");
      } else {
        throw new RuntimeException("')' expected but not found");
      }
    } else {
      throw new RuntimeException("'(' expected but not found");
    }
  }
}
