package ast.node;

import ast.expr.Expression;
import ast.obj.AttributeObject;

public class PrintNode implements Node {

  private final Expression<AttributeObject> expression;

  public PrintNode(Expression<AttributeObject> expression) {
    this.expression = expression;
  }

  public Expression<AttributeObject> getExpression() {
    return expression;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
