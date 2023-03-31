package parser.expr;

import lexer.Token;

public class UnaryExpression implements Expression<Object>{

    private final String value;

    public UnaryExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visitExpr(this);
    }
}
