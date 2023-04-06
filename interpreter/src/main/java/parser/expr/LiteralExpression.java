package parser.expr;

import interpreter.MyObject;

public class LiteralExpression implements Expression<MyObject>{

    private final MyObject value;

    public LiteralExpression(MyObject value) {
        this.value = value;
    }

    public MyObject getValue() {
        return value;
    }

    @Override
    public MyObject accept(ExpressionVisitor<MyObject> visitor) {
        return visitor.visitExpr(this);
    }
}
