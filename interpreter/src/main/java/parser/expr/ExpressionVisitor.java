package parser.expr;

public interface ExpressionVisitor<T> {

    T visitExpr(BinaryExpression binaryExpression);
    T visitExpr(LiteralExpression literalExpression);
    T visitExpr(UnaryExpression unaryExpression);
    T visitExpr(VariableExpression variableExpression);
}
