package ast.expr;

import ast.obj.AttributeObject;

public class UnaryExpression implements Expression<AttributeObject> {

  private final String value;

  public UnaryExpression(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public AttributeObject accept(ExpressionVisitor<AttributeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
