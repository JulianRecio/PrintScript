package ast.expr;

import ast.obj.CheckTypeObject;

public class VariableExpression implements Expression<CheckTypeObject> {

  private final String variableName;

  public VariableExpression(String variableName) {
    this.variableName = variableName;
  }

  public String getVariableName() {
    return variableName;
  }

  @Override
  public CheckTypeObject accept(ExpressionVisitor<CheckTypeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
