package staticCodeAnalyser.rules;

import ast.expr.*;
import ast.node.*;

public class PrintLnConditionRule implements NodeVisitor, ExpressionVisitor {

  private final boolean printLnCondition;

  public PrintLnConditionRule(boolean printLnCondition) {
    this.printLnCondition = printLnCondition;
  }

  @Override
  public void visitNode(DeclarationNode node) {}

  @Override
  public void visitNode(AssignationNode node) {}

  @Override
  public void visitNode(PrintNode node) {
    if (printLnCondition) {
      Object printExpression = node.getExpression().accept(this);

      if (printExpression.equals(ExpressionType.BINARY)
          || printExpression.equals(ExpressionType.VARIABLE)
          || printExpression.equals(ExpressionType.READ_INPUT)) {
        throw new RuntimeException("printLn argument not valid: " + printExpression);
      }
    }
  }

  @Override
  public void visitNode(IfNode node) {}

  @Override
  public Object visitExpr(BinaryExpression binaryExpression) {
    return ExpressionType.BINARY;
  }

  @Override
  public Object visitExpr(LiteralExpression literalExpression) {
    return ExpressionType.LITERAL;
  }

  @Override
  public Object visitExpr(VariableExpression variableExpression) {
    return ExpressionType.VARIABLE;
  }

  @Override
  public Object visitExpr(ReadInputExpression readInputExpression) {
    return ExpressionType.READ_INPUT;
  }
}
