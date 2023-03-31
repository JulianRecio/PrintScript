package parser.node;


import parser.expr.Expression;

public class AssignationNode implements Node{

    private final Expression<?> variable;
    private final Expression<?> expression;

    public AssignationNode(Expression<?> variable, Expression<?> expression) {
        this.variable = variable;
        this.expression = expression;
    }


    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
