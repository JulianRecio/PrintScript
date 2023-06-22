import ast.VariableType;
import ast.expr.BinaryExpression;
import ast.expr.LiteralExpression;
import ast.expr.ReadInputExpression;
import ast.node.DeclarationNode;
import ast.node.Node;
import ast.node.PrintNode;
import ast.obj.NumberObj;
import java.util.List;

public class ASTCases {

  public static List<Node> createDeclarationAST(String variableName) {
    return List.of(
        new DeclarationNode(
            variableName,
            true,
            VariableType.NUMBER,
            new LiteralExpression(new NumberObj(3, false))));
  }

  public static List<Node> createMultipleDeclarationAST(
      String variableName1, String variableName2) {
    return List.of(
        new DeclarationNode(
            variableName1,
            true,
            VariableType.NUMBER,
            new LiteralExpression(new NumberObj(3, false))),
        new DeclarationNode(
            variableName2,
            true,
            VariableType.NUMBER,
            new LiteralExpression(new NumberObj(3, false))));
  }

  public static List<Node> createPrintAST() {
    return List.of(
        new PrintNode(
            new BinaryExpression(
                "+",
                new LiteralExpression(new NumberObj(1, false)),
                new LiteralExpression(new NumberObj(2, false)))));
  }

  public static List<Node> createMultiplePrintAST() {
    return List.of(
        new PrintNode(
            new BinaryExpression(
                "+",
                new LiteralExpression(new NumberObj(1, false)),
                new LiteralExpression(new NumberObj(2, false)))),
        new PrintNode(
            new BinaryExpression(
                "+",
                new LiteralExpression(new NumberObj(2, false)),
                new LiteralExpression(new NumberObj(2, false)))));
  }

  public static List<Node> createReadInputAST() {
    return List.of(
        new DeclarationNode(
            "x",
            true,
            VariableType.STRING,
            new ReadInputExpression("Hello World", VariableType.STRING)));
  }
}
