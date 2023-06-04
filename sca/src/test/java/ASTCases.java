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
                variableName,
                true,
                VariableType.NUMBER,
                new LiteralExpression(new NumberObj(3, false)))));
  }

  public static AST createMultipleDeclarationAST(String variableName1, String variableName2) {
    return new AST(
        List.of(
            new DeclarationNode(
                variableName1,
                true,
                VariableType.NUMBER,
                new LiteralExpression(new NumberObj(3, false))),
            new DeclarationNode(
                variableName2,
                true,
                VariableType.NUMBER,
                new LiteralExpression(new NumberObj(3, false)))));
  }

  public static AST createPrintAST() {
    return new AST(
        List.of(
            new PrintNode(
                new BinaryExpression(
                    "+",
                    new LiteralExpression(new NumberObj(1, false)),
                    new LiteralExpression(new NumberObj(2, false))))));
  }

  public static AST createMultiplePrintAST() {
    return new AST(
        List.of(
            new PrintNode(
                new BinaryExpression(
                    "+",
                    new LiteralExpression(new NumberObj(1, false)),
                    new LiteralExpression(new NumberObj(2, false)))),
            new PrintNode(
                new BinaryExpression(
                    "+",
                    new LiteralExpression(new NumberObj(2, false)),
                    new LiteralExpression(new NumberObj(2, false))))));
  }

  public static AST createReadInputAST() {
    return new AST(
        List.of(
            new DeclarationNode(
                "x", true, VariableType.STRING, new ReadInputExpression("Hello World"))));
  }
}
