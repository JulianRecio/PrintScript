package parser.expressionParser;

import ast.expr.BinaryExpression;
import ast.expr.Expression;
import ast.obj.AttributeObject;
import com.google.common.collect.PeekingIterator;
import parser.ExpressionParser;
import token.Token;
import token.TokenType;

public class ExprParser implements ExpressionParser {

  private final PeekingIterator<Token> tokenIterator;

  public ExprParser(PeekingIterator<Token> tokenIterator) {
    this.tokenIterator = tokenIterator;
  }

  @Override
  public Expression<AttributeObject> parseExpression(Token token) {
    Expression<AttributeObject> left = term(token);
    Token newToken = tokenIterator.peek();
    while (newToken.getType() != TokenType.END) {
      if (newToken.getType() == TokenType.OPERATOR && "+-".contains(newToken.getValue())) {
        tokenIterator.remove();
        Expression<AttributeObject> right = term(tokenIterator.next());
        left = new BinaryExpression(newToken.getValue(), left, right);
        newToken = tokenIterator.peek();
      } else {
        break;
      }
    }
    return left;
  }

  private Expression<AttributeObject> term(Token token2) {
    Expression<AttributeObject> left = new FactorParser(tokenIterator).parseExpression(token2);
    Token token = tokenIterator.peek();
    while (token.getType() != TokenType.END) {
      if (token.getType() == TokenType.OPERATOR && "/*".contains(token.getValue())) {
        tokenIterator.remove();
        Expression<AttributeObject> right =
            new FactorParser(tokenIterator).parseExpression(tokenIterator.next());
        left = new BinaryExpression(token.getValue(), left, right);
        token = tokenIterator.peek();
      } else {
        break;
      }
    }
    return left;
  }
}
