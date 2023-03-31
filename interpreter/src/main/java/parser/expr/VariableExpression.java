package parser.expr;

public class VariableExpression implements Expression<Object>{

    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visitExpr(this);
    }
}
