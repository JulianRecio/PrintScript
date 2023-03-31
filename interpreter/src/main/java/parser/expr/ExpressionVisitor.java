package parser.expr;

public interface ExpressionVisitor<T> {

    T visit(BinaryExpression binaryExpression);
    T visit(LiteralExpression literalExpression);
    T visit(UnaryExpression unaryExpression);
    T visit(VariableExpression variableExpression);
}
