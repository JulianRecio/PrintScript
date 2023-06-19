package parser;

import ast.VariableType;
import ast.expr.*;
import ast.node.*;
import ast.obj.AttributeObject;
import ast.obj.BooleanObj;
import ast.obj.NumberObj;
import ast.obj.StringObj;
import com.google.common.collect.PeekingIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.javatuples.Pair;
import token.Token;
import token.TokenType;

public class Parser {

  private final Double version;
  private final PeekingIterator<Token> tokenIterator;
  private final Iterator<Node> nodeIterator;

  public Parser(PeekingIterator<Token> tokenIterator, Double version) {
    this.version = version;
    this.tokenIterator = tokenIterator;
    this.nodeIterator =
        new Iterator<>() {
          @Override
          public boolean hasNext() {
            return false;
          }

          @Override
          public Node next() {
            return parse();
          }
        };
  }

  public Node parse() {
    if (tokenIterator.hasNext()) {
      Token token = tokenIterator.next();
      if (token.getType() == TokenType.KEYWORD) {
        return createDeclarationNodeA(token.getValue());
      } else if (token.getType() == TokenType.IDENTIFIER) {
        return createAssignationNode(token.getValue());
      } else if (token.getType() == TokenType.PRINT) {
        return createPrintNode();
      } else if (version == 1.1 && token.getType() == TokenType.IF) {
        return createIfNode();
      } else {
        throw new RuntimeException("Line starts with incorrect token");
      }
    } else throw new RuntimeException("There are no more tokens");
  }

  private Node createAssignationNode(String variableName) {
    if (tokenIterator.next().getType() == TokenType.EQUAL) {
      Token token = tokenIterator.next();
      Pair<Expression<AttributeObject>, Token> right = expression(token);
      if (right.getValue1().getType() == TokenType.END) {
        return new AssignationNode(variableName, right.getValue0());
      } else throw new RuntimeException("; was expected but not found");
    } else throw new RuntimeException("= was expected but not found");
  }

  private Node createDeclarationNodeA(String keyWordValue) {
    boolean modifiable = getModifiable(keyWordValue);
    Pair<String, VariableType> declaration = setType();
    Token token = tokenIterator.next();
    if (token.getType() == TokenType.END) {
      return new DeclarationNode(
          declaration.getValue0(), modifiable, declaration.getValue1(), null);
    } else if (token.getType() == TokenType.EQUAL) {
      Token token2 = tokenIterator.next();
      if (token2.getType() == TokenType.READ_INPUT && version == 1.1) {
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
      } else {
        Pair<Expression<AttributeObject>, Token> expression = expression(token2);
        if (expression.getValue1().getType() == TokenType.END) {
          return new DeclarationNode(
              declaration.getValue0(), modifiable, declaration.getValue1(), expression.getValue0());
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

  private Pair<Expression<AttributeObject>, Token> expression(Token token2) {
    Pair<Expression<AttributeObject>, Token> left = term(token2);
    if (tokenIterator.hasNext() && left.getValue1().getType() != TokenType.END) {
      while (left.getValue1().getType() != TokenType.END) {
        if (left.getValue1().getType() == TokenType.OPERATOR
            && "+-".contains(left.getValue1().getValue())) {
          Pair<Expression<AttributeObject>, Token> right = term(tokenIterator.next());
          left =
              new Pair<>(
                  new BinaryExpression(
                      left.getValue1().getValue(), left.getValue0(), right.getValue0()),
                  right.getValue1());
        } else {
          break;
        }
      }
    }
    return new Pair<>(left.getValue0(), left.getValue1());
  }

  private Pair<Expression<AttributeObject>, Token> term(Token token2) {
    Expression<AttributeObject> left = factor(token2);
    Token token = tokenIterator.next();
    while (token.getType() != TokenType.END) {
      if (token.getType() == TokenType.OPERATOR && "/*".contains(token.getValue())) {
        Expression<AttributeObject> right = factor(tokenIterator.next());
        left = new BinaryExpression(token.getValue(), left, right);
      } else {
        break;
      }
      token = tokenIterator.next();
    }

    return new Pair<>(left, token);
  }

  private Expression<AttributeObject> factor(Token token) {
    if (token.getType() == TokenType.NUMBER_VALUE) {
      String numberValue = token.getValue();
      Number number;
      if (numberValue.contains(".")) {
        number = Double.parseDouble(numberValue);
      } else {
        number = Integer.parseInt(numberValue);
      }
      return new LiteralExpression(new NumberObj(number, false));
    } else if (token.getType() == TokenType.STRING_VALUE) {
      return new LiteralExpression(new StringObj(token.getValue(), false));
    } else if (token.getType() == TokenType.BOOLEAN_VALUE) {
      return new LiteralExpression(new BooleanObj(Boolean.parseBoolean(token.getValue()), false));
    } else if (token.getType() == TokenType.IDENTIFIER) {
      return new VariableExpression(token.getValue());
    } else if (token.getType() == TokenType.LEFT_PARENTHESIS) {
      Pair<Expression<AttributeObject>, Token> expression = expression(tokenIterator.next());
      if (expression.getValue1().getType() == TokenType.RIGHT_PARENTHESIS) {
        return expression.getValue0();
      } else {
        throw new RuntimeException("Right parenthesis expected but not found");
      }
    } else {
      throw new RuntimeException("Expression not found");
    }
  }

  private Node createPrintNode() {
    if (tokenIterator.next().getType() == TokenType.LEFT_PARENTHESIS) {
      Token token = tokenIterator.next();
      Pair<Expression<AttributeObject>, Token> expression = expression(token);
      if (expression.getValue1().getType() == TokenType.RIGHT_PARENTHESIS) {
        if (tokenIterator.next().getType() == TokenType.END) {
          return new PrintNode(expression.getValue0());
        } else throw new RuntimeException("; was expected but not found");
      } else {
        throw new RuntimeException("')' expected but not found");
      }
    } else {
      throw new RuntimeException("'(' expected but not found");
    }
  }

  private Node createIfNode() {
    if (tokenIterator.next().getType() == TokenType.LEFT_PARENTHESIS) {
      Expression<AttributeObject> expr = factor(tokenIterator.next());
      if (tokenIterator.next().getType() == TokenType.RIGHT_PARENTHESIS) {
        if (tokenIterator.next().getType() == TokenType.LEFT_BRACKET) {
          List<Node> ifNodes = parseIfNode();
          Token token = tokenIterator.peek();
          if (tokenIterator.hasNext() && token.getType() == TokenType.ELSE) {
            tokenIterator.remove();
            if (tokenIterator.next().getType() == TokenType.LEFT_BRACKET) {
              List<Node> elseNodes = this.parseIfNode();
              return new IfNode(expr, ifNodes, elseNodes);
            } else throw new RuntimeException("{ expected but not found");
          } else return new IfNode(expr, ifNodes, null);
        } else throw new RuntimeException("{ expected but not found");
      } else {
        throw new RuntimeException("')' expected but not found");
      }
    } else {
      throw new RuntimeException("'(' expected but not found");
    }
  }

  public List<Node> parseIfNode() {
    List<Node> nodes = new ArrayList<>();
    if (tokenIterator.hasNext()) {
      Token token = tokenIterator.next();
      while (token.getType() != TokenType.RIGHT_BRACKET) {
        if (token.getType() == TokenType.KEYWORD) {
          nodes.add(createDeclarationNodeA(token.getValue()));
        } else if (token.getType() == TokenType.IDENTIFIER) {
          nodes.add(createAssignationNode(token.getValue()));
        } else if (token.getType() == TokenType.PRINT) {
          nodes.add(createPrintNode());
        } else if (version == 1.1 && token.getType() == TokenType.IF) {
          nodes.add(createIfNode());
        } else {
          throw new RuntimeException("Line starts with incorrect token");
        }
        token = tokenIterator.next();
      }
      return nodes;
    } else throw new RuntimeException("} expected");
  }

  public Iterator<Node> getNodeIterator() {
    return nodeIterator;
  }
}
