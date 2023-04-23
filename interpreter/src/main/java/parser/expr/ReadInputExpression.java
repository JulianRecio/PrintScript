package parser.expr;

import interpreter.MyObject;

public class ReadInputExpression implements Expression<MyObject> {

  private String message;

  public ReadInputExpression(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public MyObject accept(ExpressionVisitor<MyObject> visitor) {
    return visitor.visitExpr(this);
  }
}
