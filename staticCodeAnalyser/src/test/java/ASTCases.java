import ast.AST;
import ast.VariableType;
import ast.expr.BinaryExpression;
import ast.expr.LiteralExpression;
import ast.expr.ReadInputExpression;
import ast.node.DeclarationNode;
import ast.node.PrintNode;
import ast.obj.NumberObj;
import java.util.List;

public class ASTCases {

  public static AST createDeclarationAST(String variableName) {
    return new AST(
        List.of(
            new DeclarationNode(
                variableName, VariableType.NUMBER, new LiteralExpression(new NumberObj(3.0)))));
  }

  public static AST createMultipleDeclarationAST(String variableName1, String variableName2) {
    return new AST(
        List.of(
            new DeclarationNode(
                variableName1, VariableType.NUMBER, new LiteralExpression(new NumberObj(3.0))),
            new DeclarationNode(
                variableName2, VariableType.NUMBER, new LiteralExpression(new NumberObj(3.0)))));
  }

  public static AST createPrintAST() {
    return new AST(
        List.of(
            new PrintNode(
                new BinaryExpression(
                    "+",
                    new LiteralExpression(new NumberObj(1.0)),
                    new LiteralExpression(new NumberObj(2.0))))));
  }

  public static AST createMultiplePrintAST() {
    return new AST(
        List.of(
            new PrintNode(
                new BinaryExpression(
                    "+",
                    new LiteralExpression(new NumberObj(1.0)),
                    new LiteralExpression(new NumberObj(2.0)))),
            new PrintNode(
                new BinaryExpression(
                    "+",
                    new LiteralExpression(new NumberObj(2.0)),
                    new LiteralExpression(new NumberObj(2.0))))));
  }

  public static AST createReadInputAST() {
    return new AST(
        List.of(
            new DeclarationNode("x", VariableType.STRING, new ReadInputExpression("Hello World"))));
  }
}
