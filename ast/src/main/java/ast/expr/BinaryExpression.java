package ast.expr;

import ast.obj.AttributeObject;

public class BinaryExpression implements Expression<AttributeObject> {

  private final String operator;
  private final Expression<AttributeObject> left;
  private final Expression<AttributeObject> right;

  public BinaryExpression(
      String operator, Expression<AttributeObject> left, Expression<AttributeObject> right) {
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  public String getOperator() {
    return operator;
  }

  public Expression<AttributeObject> getLeft() {
    return left;
  }

  public Expression<AttributeObject> getRight() {
    return right;
  }

  @Override
  public AttributeObject accept(ExpressionVisitor<AttributeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
