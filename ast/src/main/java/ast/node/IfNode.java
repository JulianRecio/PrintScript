package ast.node;

import ast.AST;
import ast.expr.Expression;
import ast.obj.CheckTypeObject;

public class IfNode implements Node {

  private Expression<CheckTypeObject> value;
  private AST ifAST;
  private AST elseAST;

  public IfNode(Expression<CheckTypeObject> value, AST ifAST, AST elseAST) {
    this.value = value;
    this.ifAST = ifAST;
    this.elseAST = elseAST;
  }

  public Expression<CheckTypeObject> getValue() {
    return value;
  }

  public AST getIfAST() {
    return ifAST;
  }

  public AST getElseAST() {
    return elseAST;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
