package parser.expr;

import interpreter.MyObject;
import lexer.Token;

public class UnaryExpression implements Expression<MyObject>{

    private final String value;

    public UnaryExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public MyObject accept(ExpressionVisitor<MyObject> visitor) {
        return visitor.visitExpr(this);
    }
}
