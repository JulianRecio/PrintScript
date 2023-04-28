package ast.node;

import ast.expr.Expression;
import ast.obj.CheckTypeObject;

public class PrintNode implements Node {

  private final Expression<CheckTypeObject> expression;

  public PrintNode(Expression<CheckTypeObject> expression) {
    this.expression = expression;
  }

  public Expression<CheckTypeObject> getExpression() {
    return expression;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
