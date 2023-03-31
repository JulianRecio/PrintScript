package parser.expr;

public class LiteralExpression implements Expression<Object>{

    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor<Object> visitor) {
        return visitor.visitExpr(this);
    }
}
