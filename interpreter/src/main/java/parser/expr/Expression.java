package parser.expr;

public interface Expression<T> {

    T accept(ExpressionVisitor<T> visitor);
}
