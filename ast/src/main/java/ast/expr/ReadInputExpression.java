package ast.expr;

import ast.obj.CheckTypeObject;

public class ReadInputExpression implements Expression<CheckTypeObject> {

  private String message;

  public ReadInputExpression(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public CheckTypeObject accept(ExpressionVisitor<CheckTypeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
