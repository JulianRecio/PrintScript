package ast.node;

import ast.AST;
import ast.expr.Expression;
import ast.obj.AttributeObject;

public class IfNode implements Node {

  private Expression<AttributeObject> value;
  private AST ifAST;
  private AST elseAST;

  public IfNode(Expression<AttributeObject> value, AST ifAST, AST elseAST) {
    this.value = value;
    this.ifAST = ifAST;
    this.elseAST = elseAST;
  }

  public Expression<AttributeObject> getValue() {
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
