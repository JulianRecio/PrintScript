package ast.expr;

import ast.obj.CheckTypeObject;

public class UnaryExpression implements Expression<CheckTypeObject> {

  private final String value;

  public UnaryExpression(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public CheckTypeObject accept(ExpressionVisitor<CheckTypeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
