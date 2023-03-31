package parser.expr;

import lexer.Token;

public class VariableExpression implements Expression<Object>{

    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visit(this);
    }
}
