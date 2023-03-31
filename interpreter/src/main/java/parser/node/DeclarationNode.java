package parser.node;


import parser.VariableType;
import parser.expr.Expression;

public class DeclarationNode implements Node{   //When I only declare the variable with no value

    private final String variableName;
    private final VariableType type;
    private final Expression<?> initializer;

    public DeclarationNode(String variableName, VariableType type, Expression<?> initializer) {
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

    public Expression<?> getInitializer() {
        return initializer;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitNode(this);
    }


}
