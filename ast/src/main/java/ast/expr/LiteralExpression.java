package ast.expr;

import ast.obj.AttributeObject;

public class LiteralExpression implements Expression<AttributeObject> {

  private final AttributeObject value;

  public LiteralExpression(AttributeObject value) {
    this.value = value;
  }

  public AttributeObject getValue() {
    return value;
  }

  @Override
  public AttributeObject accept(ExpressionVisitor<AttributeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
