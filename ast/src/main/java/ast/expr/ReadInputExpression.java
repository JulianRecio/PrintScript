package ast.expr;

import ast.obj.AttributeObject;

public class ReadInputExpression implements Expression<AttributeObject> {

  private String message;

  public ReadInputExpression(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public AttributeObject accept(ExpressionVisitor<AttributeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
