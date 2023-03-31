package parser.expr;

public class BinaryExpression implements Expression<Object>{

    private final String operator;
    private final Expression<Object> left;
    private final Expression<Object> right;

    public BinaryExpression(String operator, Expression<Object> left, Expression<Object> right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public Expression<Object> getLeft() {
        return left;
    }

    public Expression<Object> getRight() {
        return right;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visitExpr(this);
    }
}
