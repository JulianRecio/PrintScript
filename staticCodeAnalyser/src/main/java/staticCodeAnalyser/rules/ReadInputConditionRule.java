package staticCodeAnalyser.rules;

import ast.expr.*;
import ast.node.*;

public class ReadInputConditionRule implements NodeVisitor, ExpressionVisitor {

  private final boolean readInputCondition;

  public ReadInputConditionRule(boolean readInputCondition) {
    this.readInputCondition = readInputCondition;
  }

  @Override
  public void visitNode(DeclarationNode node) {}

  @Override
  public void visitNode(AssignationNode node) {
    if (readInputCondition) {
      Object readInputExpression = node.getExpression().accept(this);

      if (readInputExpression.equals(ExpressionType.BINARY)
          || readInputExpression.equals(ExpressionType.UNARY)) {
        throw new RuntimeException("readInput argument not valid: " + readInputExpression);
      }
    }
  }

  @Override
  public void visitNode(PrintNode node) {}

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
  public Object visitExpr(UnaryExpression unaryExpression) {
    return ExpressionType.UNARY;
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
