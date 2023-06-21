package parser.nodeParser;

import ast.expr.Expression;
import ast.node.IfNode;
import ast.node.Node;
import ast.obj.AttributeObject;
import com.google.common.collect.PeekingIterator;
import java.util.ArrayList;
import java.util.List;
import parser.NodeParser;
import parser.expressionParser.FactorParser;
import token.Token;
import token.TokenType;

public class IfCaseParser implements NodeParser {

  private final PeekingIterator<Token> tokenIterator;
  private final double version;

  public IfCaseParser(PeekingIterator<Token> tokenIterator, double version) {
    this.tokenIterator = tokenIterator;
    this.version = version;
  }

  @Override
  public Node parseNode() {
    if (tokenIterator.next().getType() == TokenType.LEFT_PARENTHESIS) {
      Expression<AttributeObject> expr =
          new FactorParser(tokenIterator).parseExpression(tokenIterator.next());
      if (tokenIterator.next().getType() == TokenType.RIGHT_PARENTHESIS) {
        if (tokenIterator.next().getType() == TokenType.LEFT_BRACKET) {
          List<Node> ifNodes = parseIfNode();
          Token token = tokenIterator.peek();
          if (tokenIterator.hasNext() && token.getType() == TokenType.ELSE) {
            tokenIterator.remove();
            if (tokenIterator.next().getType() == TokenType.LEFT_BRACKET) {
              List<Node> elseNodes = this.parseIfNode();
              return new IfNode(expr, ifNodes, elseNodes);
            } else throw new RuntimeException("{ expected but not found");
          } else return new IfNode(expr, ifNodes, null);
        } else throw new RuntimeException("{ expected but not found");
      } else {
        throw new RuntimeException("')' expected but not found");
      }
    } else {
      throw new RuntimeException("'(' expected but not found");
    }
  }

  public List<Node> parseIfNode() {
    List<Node> nodes = new ArrayList<>();
    if (tokenIterator.hasNext()) {
      Token token = tokenIterator.peek();
      while (token.getType() != TokenType.RIGHT_BRACKET) {
        if (token.getType() == TokenType.KEYWORD) {
          nodes.add(new DeclarationParser(tokenIterator, version).parseNode());
        } else if (token.getType() == TokenType.IDENTIFIER) {
          nodes.add(new AssignationParser(tokenIterator).parseNode());
        } else if (token.getType() == TokenType.PRINT) {
          tokenIterator.remove();
          nodes.add(new PrintParser(tokenIterator).parseNode());
        } else if (version == 1.1 && token.getType() == TokenType.IF) {
          tokenIterator.remove();
          nodes.add(new IfCaseParser(tokenIterator, version).parseNode());
        } else {
          throw new RuntimeException("Line starts with incorrect token");
        }
        token = tokenIterator.peek();
      }
      tokenIterator.remove();
      return nodes;
    } else throw new RuntimeException("} expected");
  }
}
