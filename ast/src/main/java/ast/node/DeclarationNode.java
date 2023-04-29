package ast.node;

import ast.VariableType;
import ast.expr.Expression;
import ast.obj.AttributeObject;

public class DeclarationNode implements Node { // When I only declare the variable with no value

  private final String variableName;
  private final boolean modifiable;
  private final VariableType type;
  private final Expression<AttributeObject> initializer;

  public DeclarationNode(
      String variableName,
      boolean modifiable,
      VariableType type,
      Expression<AttributeObject> initializer) {
    this.variableName = variableName;
    this.modifiable = modifiable;
    this.type = type;
    this.initializer = initializer;
  }

  public String getVariableName() {
    return variableName;
  }

  public boolean isModifiable() {
    return modifiable;
  }

  public VariableType getType() {
    return type;
  }

  public Expression<AttributeObject> getInitializer() {
    return initializer;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visitNode(this);
  }
}
