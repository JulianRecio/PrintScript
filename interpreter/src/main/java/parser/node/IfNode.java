package parser.node;

import interpreter.MyObject;
import parser.AST;
import parser.expr.Expression;

public class IfNode implements Node {

  private Expression<MyObject> value;
  private AST ifAST;
  private AST elseAST;

  public IfNode(Expression<MyObject> value, AST ifAST, AST elseAST) {
    this.value = value;
    this.ifAST = ifAST;
    this.elseAST = elseAST;
  }

  public Expression<MyObject> getValue() {
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
