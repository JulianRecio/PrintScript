package parser.expr;

import lexer.Token;

public class BinaryExpression implements Expression<Object>{

    private final String operator;
    private final Expression<Object> left;
    private final Expression<Object> right;

    public BinaryExpression(String operator, Expression<Object> left, Expression<Object> right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visit(this);
    }
}
