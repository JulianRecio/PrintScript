package ast.expr;

import ast.VariableType;
import ast.obj.AttributeObject;

public class ReadInputExpression implements Expression<AttributeObject> {

  private String message;
  private VariableType variableType;

  public ReadInputExpression(String message, VariableType variableType) {
    this.message = message;
    this.variableType = variableType;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public AttributeObject accept(ExpressionVisitor<AttributeObject> visitor) {
    return visitor.visitExpr(this);
  }

  public VariableType getVariableType() {
    return variableType;
  }
}
