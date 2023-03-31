package parser.node;


import parser.expr.Expression;

public class PrintNode implements Node{

    private final Expression<?> expression;

    public PrintNode(Expression<?> expression) {
        this.expression = expression;
    }

    public Expression<?> getExpression() {
        return expression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitNode(this);
    }
}
