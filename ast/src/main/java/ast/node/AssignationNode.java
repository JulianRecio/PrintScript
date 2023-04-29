package ast.node;

import ast.expr.Expression;
import ast.obj.AttributeObject;

public class AssignationNode implements Node {

  private final String variable;
  private final Expression<AttributeObject> expression;

  public AssignationNode(String variable, Expression<AttributeObject> expression) {
    this.variable = variable;
    this.expression = expression;
  }

  public String getVariable() {
    return variable;
  }

  public Expression<AttributeObject> getExpression() {
    return expression;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
