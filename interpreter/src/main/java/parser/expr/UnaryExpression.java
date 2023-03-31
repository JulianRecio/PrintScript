package parser.expr;

import lexer.Token;

public class UnaryExpression implements Expression<Object>{

    private final Token operator;
    private final Expression<Object> right;

    public UnaryExpression(Token operator, Expression<Object> right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visit(this);
    }
}
