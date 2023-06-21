package parser.expressionParser;

import ast.expr.Expression;
import ast.expr.LiteralExpression;
import ast.expr.VariableExpression;
import ast.obj.AttributeObject;
import ast.obj.BooleanObj;
import ast.obj.NumberObj;
import ast.obj.StringObj;
import com.google.common.collect.PeekingIterator;
import parser.ExpressionParser;
import token.Token;
import token.TokenType;

public class FactorParser implements ExpressionParser {

  private final PeekingIterator<Token> tokenIterator;

  public FactorParser(PeekingIterator<Token> tokenIterator) {
    this.tokenIterator = tokenIterator;
  }

  @Override
  public Expression<AttributeObject> parseExpression(Token token) {
    if (token.getType() == TokenType.NUMBER_VALUE) {
      String numberValue = token.getValue();
      Number number;
      if (numberValue.contains(".")) {
        number = Double.parseDouble(numberValue);
      } else {
        number = Integer.parseInt(numberValue);
      }
      return new LiteralExpression(new NumberObj(number, false));
    } else if (token.getType() == TokenType.STRING_VALUE) {
      return new LiteralExpression(new StringObj(token.getValue(), false));
    } else if (token.getType() == TokenType.BOOLEAN_VALUE) {
      return new LiteralExpression(new BooleanObj(Boolean.parseBoolean(token.getValue()), false));
    } else if (token.getType() == TokenType.IDENTIFIER) {
      return new VariableExpression(token.getValue());
    } else if (token.getType() == TokenType.LEFT_PARENTHESIS) {
      Expression<AttributeObject> expression =
          new ExprParser(tokenIterator).parseExpression(tokenIterator.next());
      if (tokenIterator.peek().getType() == TokenType.RIGHT_PARENTHESIS) {
        tokenIterator.remove();
        return expression;
      } else {
        throw new RuntimeException("Right parenthesis expected but not found");
      }
    } else {
      throw new RuntimeException("Expression not found");
    }
  }
}
