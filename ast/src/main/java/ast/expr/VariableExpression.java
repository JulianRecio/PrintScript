package ast.expr;

import ast.obj.AttributeObject;

public class VariableExpression implements Expression<AttributeObject> {

  private final String variableName;

  public VariableExpression(String variableName) {
    this.variableName = variableName;
  }

  public String getVariableName() {
    return variableName;
  }

  @Override
  public AttributeObject accept(ExpressionVisitor<AttributeObject> visitor) {
    return visitor.visitExpr(this);
  }
}
