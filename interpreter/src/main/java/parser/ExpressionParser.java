package parser;

import ast.expr.Expression;
import ast.obj.AttributeObject;
import token.Token;

public interface ExpressionParser {

  Expression<AttributeObject> parseExpression(Token token);
}
