package parser.nodeParser;

import ast.VariableType;
import ast.expr.Expression;
import ast.expr.ReadInputExpression;
import ast.node.DeclarationNode;
import ast.node.Node;
import ast.obj.AttributeObject;
import com.google.common.collect.PeekingIterator;
import org.javatuples.Pair;
import parser.NodeParser;
import parser.expressionParser.ExprParser;
import token.Token;
import token.TokenType;

public class DeclarationParser implements NodeParser {

  private final PeekingIterator<Token> tokenIterator;
  private final double version;

  public DeclarationParser(PeekingIterator<Token> tokenIterator, double version) {
    this.tokenIterator = tokenIterator;
    this.version = version;
  }

  @Override
  public Node parseNode() {
    boolean modifiable = getModifiable(tokenIterator.next().getValue());
    Pair<String, VariableType> declaration = setType();
    Token token = tokenIterator.next();
    if (token.getType() == TokenType.END) {
      return new DeclarationNode(
          declaration.getValue0(), modifiable, declaration.getValue1(), null);
    } else if (token.getType() == TokenType.EQUAL) {
      Token token2 = tokenIterator.next();
      if (token2.getType() == TokenType.READ_INPUT && version == 1.1) {
        return readInputDeclarationNode(modifiable, declaration);
      } else {
        Expression<AttributeObject> expression =
            new ExprParser(tokenIterator).parseExpression(token2);
        if (tokenIterator.next().getType() == TokenType.END) {
          return new DeclarationNode(
              declaration.getValue0(), modifiable, declaration.getValue1(), expression);
        } else throw new RuntimeException("; was expected but not found");
      }
    } else {
      throw new RuntimeException("= or ; tokens were expected but not found");
    }
  }

  private boolean getModifiable(String keyWordValue) {
    if (keyWordValue.equals("let")) {
      return true;
    } else if (keyWordValue.equals("const")) {
      return false;
    } else throw new RuntimeException("keyword not supported");
  }

  private Pair<String, VariableType> setType() {
    Token token = tokenIterator.next();
    if (token.getType() == TokenType.IDENTIFIER) {
      String identifier = token.getValue();
      if (tokenIterator.next().getType() == TokenType.ALLOCATOR) {
        Token token1 = tokenIterator.next();
        if (token1.getType() == TokenType.TYPE) {
          String type = token1.getValue();
          if (type.equals("number")) {
            return new Pair<>(identifier, VariableType.NUMBER);
          } else if (type.equals("boolean")) {
            return new Pair<>(identifier, VariableType.BOOLEAN);
          } else return new Pair<>(identifier, VariableType.STRING);
        } else throw new RuntimeException("There is no type after allocation");
      } else throw new RuntimeException("There is no : after variable");
    } else throw new RuntimeException("There is no variable name after keyword");
  }

  private DeclarationNode readInputDeclarationNode(
      boolean modifiable, Pair<String, VariableType> declaration) {
    if (tokenIterator.next().getType() == TokenType.LEFT_PARENTHESIS) {
      Token token1 = tokenIterator.next();
      if (token1.getType() == TokenType.STRING_VALUE) {
        String message = token1.getValue();
        if (tokenIterator.next().getType() == TokenType.RIGHT_PARENTHESIS) {
          if (tokenIterator.next().getType() == TokenType.END) {
            return new DeclarationNode(
                declaration.getValue0(),
                modifiable,
                declaration.getValue1(),
                new ReadInputExpression(message, declaration.getValue1()));
          } else throw new RuntimeException("; was expected but not found");
        } else throw new RuntimeException(") expected");
      } else throw new RuntimeException("message expected");
    } else throw new RuntimeException("( expected");
  }
}
