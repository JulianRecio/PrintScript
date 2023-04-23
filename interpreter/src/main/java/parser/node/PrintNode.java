package parser.node;

import interpreter.MyObject;
import parser.expr.Expression;

public class PrintNode implements Node {

  private final Expression<MyObject> expression;

  public PrintNode(Expression<MyObject> expression) {
    this.expression = expression;
  }

  public Expression<MyObject> getExpression() {
    return expression;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
