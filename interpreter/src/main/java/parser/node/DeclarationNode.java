package parser.node;


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

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }


}
