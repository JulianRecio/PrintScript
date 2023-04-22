package parser.node;


import interpreter.MyObject;
import parser.expr.Expression;

public class AssignationNode implements Node{

    private final String variable;
    private final Expression<MyObject> expression;

    public AssignationNode(String variable, Expression<MyObject> expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public Expression<MyObject> getExpression() {
        return expression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitNode(this);
    }
}
