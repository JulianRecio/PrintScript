package parser.node;


import parser.expr.Expression;

public class AssignationNode implements Node{

    private final String variable;
    private final Expression<?> expression;

    public AssignationNode(String variable, Expression<?> expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public Expression<?> getExpression() {
        return expression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitNode(this);
    }
}
