package ast.node;

import ast.expr.Expression;
import ast.obj.CheckTypeObject;

public class AssignationNode implements Node {

  private final String variable;
  private final Expression<CheckTypeObject> expression;

  public AssignationNode(String variable, Expression<CheckTypeObject> expression) {
    this.variable = variable;
    this.expression = expression;
  }

  public String getVariable() {
    return variable;
  }

  public Expression<CheckTypeObject> getExpression() {
    return expression;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
