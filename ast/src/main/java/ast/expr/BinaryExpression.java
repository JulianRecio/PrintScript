package ast.expr;

import ast.obj.CheckTypeObject;

public class BinaryExpression implements Expression<CheckTypeObject> {

  private final String operator;
  private final Expression<CheckTypeObject> left;
  private final Expression<CheckTypeObject> right;

  public BinaryExpression(
      String operator, Expression<CheckTypeObject> left, Expression<CheckTypeObject> right) {
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  public String getOperator() {
    return operator;
  }

  public Expression<CheckTypeObject> getLeft() {
    return left;
  }

  public Expression<CheckTypeObject> getRight() {
    return right;
  }

  @Override
  public CheckTypeObject accept(ExpressionVisitor<CheckTypeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
