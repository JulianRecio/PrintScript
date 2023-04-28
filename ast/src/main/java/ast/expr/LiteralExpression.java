package ast.expr;

import ast.obj.CheckTypeObject;

public class LiteralExpression implements Expression<CheckTypeObject> {

  private final CheckTypeObject value;

  public LiteralExpression(CheckTypeObject value) {
    this.value = value;
  }

  public CheckTypeObject getValue() {
    return value;
  }

  @Override
  public CheckTypeObject accept(ExpressionVisitor<CheckTypeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
