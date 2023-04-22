package staticCodeAnalyser.rules;

import parser.expr.*;
import parser.node.AssignationNode;
import parser.node.DeclarationNode;
import parser.node.NodeVisitor;
import parser.node.PrintNode;

public class PrintConditionRule implements NodeVisitor, ExpressionVisitor {

    private final boolean printlnCondition;

    public PrintConditionRule(boolean printlnCondition) {
        this.printlnCondition = printlnCondition;
    }

    @Override
    public void visitNode(DeclarationNode node) {

    }

    @Override
    public void visitNode(AssignationNode node) {

    }

    @Override
    public void visitNode(PrintNode node) {
    if (printlnCondition){
        Object printExpression = node.getExpression().accept(this);

        if (printExpression.equals(ExpressionType.BINARY) || printExpression.equals(ExpressionType.UNARY)){
            throw new RuntimeException("println argument not valid: " + printExpression);
        }
    }
    }

    @Override
    public Object visitExpr(BinaryExpression binaryExpression) {
        return ExpressionType.BINARY;
    }

    @Override
    public Object visitExpr(LiteralExpression literalExpression) {
        return ExpressionType.LITERAL;
    }

    @Override
    public Object visitExpr(UnaryExpression unaryExpression) {
        return ExpressionType.UNARY;
    }

    @Override
    public Object visitExpr(VariableExpression variableExpression) {
        return ExpressionType.VARIABLE;
    }


}
