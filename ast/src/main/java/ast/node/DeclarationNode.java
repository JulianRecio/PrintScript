package ast.node;

import ast.VariableType;
import ast.expr.Expression;
import ast.obj.CheckTypeObject;

public class DeclarationNode implements Node { // When I only declare the variable with no value

  private final String variableName;
  private final VariableType type;
  private final Expression<CheckTypeObject> initializer;

  public DeclarationNode(
      String variableName, VariableType type, Expression<CheckTypeObject> initializer) {
    this.variableName = variableName;
    this.type = type;
    this.initializer = initializer;
  }

  public String getVariableName() {
    return variableName;
  }

  public VariableType getType() {
    return type;
  }

  public Expression<CheckTypeObject> getInitializer() {
    return initializer;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
