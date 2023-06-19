package ast.node;

import ast.expr.Expression;
import ast.obj.AttributeObject;
import java.util.List;

public class IfNode implements Node {

  private Expression<AttributeObject> value;
  private List<Node> ifAST;
  private List<Node> elseAST;

  public IfNode(Expression<AttributeObject> value, List<Node> ifAST, List<Node> elseAST) {
    this.value = value;
    this.ifAST = ifAST;
    this.elseAST = elseAST;
  }

  public Expression<AttributeObject> getValue() {
    return value;
  }

  public List<Node> getIfAST() {
    return ifAST;
  }

  public List<Node> getElseAST() {
    return elseAST;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
