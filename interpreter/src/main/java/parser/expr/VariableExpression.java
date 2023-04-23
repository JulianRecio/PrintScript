package parser.expr;

import interpreter.MyObject;

public class VariableExpression implements Expression<MyObject> {

  private final String variableName;

  public VariableExpression(String variableName) {
    this.variableName = variableName;
  }

  public String getVariableName() {
    return variableName;
  }

  @Override
  public MyObject accept(ExpressionVisitor<MyObject> visitor) {
    return visitor.visitExpr(this);
  }
}
