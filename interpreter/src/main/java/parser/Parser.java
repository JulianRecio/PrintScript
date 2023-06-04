package parser;

import ast.AST;
import ast.VariableType;
import ast.expr.*;
import ast.node.*;
import ast.obj.*;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import token.Token;
import token.TokenType;

public class Parser {

  private final List<Token> tokens;
  private int pos;
  private final Double version;

  public Parser(List<Token> tokens, Double version) {
    this.tokens = tokens;
    this.pos = 0;
    this.version = version;
  }

  public AST parse() {
    List<Node> ast = new ArrayList<>();
    while (pos < tokens.size() && tokens.get(pos).getType() != TokenType.RIGHT_BRACKET) {
      if (tokens.get(pos).getType() == TokenType.KEYWORD) {
        createDeclarationNode(tokens.get(pos).getValue(), ast);
      } else if (tokens.get(pos).getType() == TokenType.IDENTIFIER) {
        createAssignationNode(ast);
      } else if (tokens.get(pos).getType() == TokenType.PRINT) {
        createPrintNode(ast);
      } else if (version == 1.1 && tokens.get(pos).getType() == TokenType.IF) {
        createIfNode(ast);
      } else {
        throw new RuntimeException("Line starts with incorrect token");
      }
    }
    return new AST(ast);
  }

  private void createDeclarationNode(String keyWordValue, List<Node> ast) {
    pos++;
    boolean modifiable = getModifiable(keyWordValue);
    Pair<String, VariableType> declaration = setType();
    if (tokens.get(pos).getType() == TokenType.END) {
      pos++;
      Node declarationNode =
          new DeclarationNode(declaration.getValue0(), modifiable, declaration.getValue1(), null);
      ast.add(declarationNode);
    } else if (tokens.get(pos).getType() == TokenType.EQUAL) {
      pos++;
      Node declarationNode =
          new DeclarationNode(
              declaration.getValue0(), modifiable, declaration.getValue1(), expression());
      if (tokens.get(pos).getType() == TokenType.END) {
        pos++;
        ast.add(declarationNode);
      } else {
        throw new RuntimeException("; was expected but not found");
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

  private void createAssignationNode(List<Node> ast) {
    String variable = tokens.get(pos).getValue();
    pos++;
    if (tokens.get(pos).getType() == TokenType.EQUAL) {
      pos++;
      Expression<AttributeObject> right = expression();
      Node assignationNode = new AssignationNode(variable, right);
      if (tokens.get(pos).getType() == TokenType.END) {
        pos++;
        ast.add(assignationNode);
      } else throw new RuntimeException("; was expected but not found");
    } else throw new RuntimeException("= was expected but not found");
  }

  private void createPrintNode(List<Node> ast) {
    pos++;
    if (tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS) {
      pos++;
      Expression<AttributeObject> expression = expression();
      if (tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS) {
        pos++;
        Node print = new PrintNode(expression);
        if (tokens.get(pos).getType() == TokenType.END) {
          pos++;
          ast.add(print);
        } else throw new RuntimeException("; was expected but not found");
      } else {
        throw new RuntimeException("')' expected but not found");
      }
    } else {
      throw new RuntimeException("'(' expected but not found");
    }
  }

  private void createIfNode(List<Node> ast) {
    pos++;
    if (tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS) {
      pos++;
      Expression<AttributeObject> expr = factor();
      if (tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS) {
        pos++;
        if (tokens.get(pos).getType() == TokenType.LEFT_BRACKET) {
          pos++;
          AST ifAST = this.parse();
          if (tokens.get(pos).getType() == TokenType.RIGHT_BRACKET) {
            pos++;
            if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.ELSE) {
              pos++;
              if (tokens.get(pos).getType() == TokenType.LEFT_BRACKET) {
                pos++;
                AST elseAST = this.parse();
                if (tokens.get(pos).getType() == TokenType.RIGHT_BRACKET) {
                  pos++;
                  ast.add(new IfNode(expr, ifAST, elseAST));
                }
              }
            } else ast.add(new IfNode(expr, ifAST, null));
          } else throw new RuntimeException("{ expected");
        }
      } else {
        throw new RuntimeException("')' expected but not found");
      }
    } else {
      throw new RuntimeException("'(' expected but not found");
    }
  }

  private Pair<String, VariableType> setType() {
    if (tokens.get(pos).getType() == TokenType.IDENTIFIER) {
      String identifier = tokens.get(pos).getValue();
      pos++;
      if (tokens.get(pos).getType() == TokenType.ALLOCATOR) {
        pos++;
        if (tokens.get(pos).getType() == TokenType.TYPE) {
          String type = tokens.get(pos).getValue();
          pos++;
          if (type.equals("number")) {
            return new Pair<>(identifier, VariableType.NUMBER);
          } else if (type.equals("boolean")) {
            return new Pair<>(identifier, VariableType.BOOLEAN);
          } else return new Pair<>(identifier, VariableType.STRING);
        } else throw new RuntimeException("There is no type after allocation");
      } else throw new RuntimeException("There is no : after variable");
    } else throw new RuntimeException("There is no variable name after keyword");
  }

  private Expression<AttributeObject> expression() {
    Expression<AttributeObject> left = term();
    while (tokens.get(pos).getType() != TokenType.END) {
      Token token = tokens.get(pos);
      if (token.getType() == TokenType.OPERATOR && "+-".contains(token.getValue())) {
        pos++;
        Expression<AttributeObject> right = term();
        left = new BinaryExpression(token.getValue(), left, right);
      } else {
        break;
      }
    }
    return left;
  }

  private Expression<AttributeObject> term() {
    Expression<AttributeObject> left = factor();
    while (tokens.get(pos).getType() != TokenType.END) {
      Token token = tokens.get(pos);
      if (token.getType() == TokenType.OPERATOR && "/*".contains(token.getValue())) {
        pos++;
        Expression<AttributeObject> right = factor();
        left = new BinaryExpression(token.getValue(), left, right);
      } else {
        break;
      }
    }
    return left;
  }

  private Expression<AttributeObject> factor() {
    if (tokens.get(pos).getType() == TokenType.NUMBER_VALUE) {
      Expression<AttributeObject> numberExpr =
          new LiteralExpression(new NumberObj(Integer.parseInt(tokens.get(pos).getValue()), false));
      pos++;
      return numberExpr;
    } else if (tokens.get(pos).getType() == TokenType.STRING_VALUE) {
      Expression<AttributeObject> stringExpr =
          new LiteralExpression(new StringObj(tokens.get(pos).getValue(), false));
      pos++;
      return stringExpr;
    } else if (tokens.get(pos).getType() == TokenType.BOOLEAN_VALUE) {
      Expression<AttributeObject> booleanExpr =
          new LiteralExpression(
              new BooleanObj(Boolean.parseBoolean(tokens.get(pos).getValue()), false));
      pos++;
      return booleanExpr;
    } else if (tokens.get(pos).getType() == TokenType.IDENTIFIER) {
      Expression<AttributeObject> identifierExpr =
          new VariableExpression(tokens.get(pos).getValue());
      pos++;
      return identifierExpr;
    } else if (tokens.get(pos).getType() == TokenType.READ_INPUT && version == 1.1) {
      pos++;
      if (tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS) {
        pos++;
        if (tokens.get(pos).getType() == TokenType.STRING_VALUE) {
          String message = tokens.get(pos).getValue();
          pos++;
          if (tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS) {
            pos++;
            return new ReadInputExpression(message);
          } else throw new RuntimeException(") expected");
        } else throw new RuntimeException("message expected");
      } else throw new RuntimeException("( expected");
    } else if (tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS) {
      pos++;
      Expression<AttributeObject> expression = expression();
      if (tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS) {
        pos++;
        return expression;
      } else {
        throw new RuntimeException("Right parenthesis expected but not found");
      }
    } else {
      throw new RuntimeException("Expression not found");
    }
  }
}
