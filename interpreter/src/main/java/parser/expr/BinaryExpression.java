package parser.expr;

import interpreter.MyObject;

public class BinaryExpression implements Expression<MyObject>{

    private final String operator;
    private final Expression<MyObject> left;
    private final Expression<MyObject> right;

    public BinaryExpression(String operator, Expression<MyObject> left, Expression<MyObject> right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public Expression<MyObject> getLeft() {
        return left;
    }

    public Expression<MyObject> getRight() {
        return right;
    }

    @Override
    public MyObject accept(ExpressionVisitor<MyObject> visitor) {
        return visitor.visitExpr(this);
    }
}
