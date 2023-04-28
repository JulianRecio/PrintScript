package ast.expr;

public interface ExpressionVisitor<T> {

  T visitExpr(BinaryExpression binaryExpression);

  T visitExpr(LiteralExpression literalExpression);

  T visitExpr(VariableExpression variableExpression);

  T visitExpr(ReadInputExpression readInputExpression);
}
