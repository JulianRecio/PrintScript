package ast.expr;

public interface Expression<T> {

  T accept(ExpressionVisitor<T> visitor);
}
