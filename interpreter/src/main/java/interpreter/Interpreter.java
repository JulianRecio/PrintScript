package interpreter;

import ast.AST;
import ast.VariableType;
import ast.expr.*;
import ast.node.*;
import ast.obj.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Interpreter implements NodeVisitor, ExpressionVisitor<AttributeObject> {

  private final AST ast;
  private final HashMap<String, AttributeObject> map = new HashMap<>();
  private final List<String> errors = new ArrayList<>();
  private final List<String> printed = new ArrayList<>();

  public Interpreter(AST ast) {
    this.ast = ast;
  }

  public void interpret() {
    List<Node> nodes = ast.getAst();
    for (Node node : nodes) {
      node.accept(this);
    }
  }

  @Override
  public void visitNode(DeclarationNode node) {
    String variable = node.getVariableName();
    VariableType type = node.getType();
    if (map.containsKey(variable)) {
      String errorMsg = "Variable " + variable + " is already declared";
      errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    } else {
      AttributeObject classType;
      if (type == VariableType.NUMBER) {
        classType = new NumberObj(node.isModifiable());
        map.put(variable, classType);
      } else if (type == VariableType.BOOLEAN) {
        classType = new BooleanObj(node.isModifiable());
        map.put(variable, classType);
      } else {
        classType = new StringObj(node.isModifiable());
        map.put(variable, classType);
      }
      if (node.getInitializer() != null) {
        AttributeObject result = node.getInitializer().accept(this);
        if (classType.typeIsCorrect(result.getValue())) {
          map.put(variable, result);
        } else {
          String errorMsg = "Mismatching types";
          errors.add(errorMsg);
          throw new RuntimeException(errorMsg);
        }
      }
    }
  }

  @Override
  public void visitNode(AssignationNode node) {
    String variable = node.getVariable();
    Expression<AttributeObject> right = node.getExpression();
    AttributeObject result = right.accept(this);
    if (map.containsKey(variable)) {
      AttributeObject value = map.get(variable);
      if (value.isModifiable()) {
        result.setModifiable(true);
        if (value.typeIsCorrect(result.getValue())) {
          map.put(variable, result);
        } else {
          String errorMsg = "Mismatching types";
          errors.add(errorMsg);
          throw new RuntimeException(errorMsg);
        }
      } else throw new RuntimeException("Variable is const, cannot change value");
    } else {
      String errorMsg = "Variable does not exist";
      errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public void visitNode(PrintNode node) {
    AttributeObject toPrint = node.getExpression().accept(this);
    if (toPrint.getValue() == null) {
      String errorMsg = "Variable was not initialized";
      errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    } else {
      printed.add(toPrint.getValue().toString());
      System.out.println(toPrint.getValue().toString());
    }
  }

  @Override
  public void visitNode(IfNode node) {
    AttributeObject obj = node.getValue().accept(this);
    if (node.getValue().accept(this) instanceof BooleanObj) {
      if ((boolean) obj.getValue()) {
        List<Node> tmpAST = node.getIfAST().getAst();
        for (Node value : tmpAST) {
          value.accept(this);
        }
      } else if (node.getElseAST() != null) {
        List<Node> tmpAST = node.getElseAST().getAst();
        for (Node value : tmpAST) {
          value.accept(this);
        }
      }
    } else {
      String errorMsg = "Expression inside if needs to be of type Boolean";
      errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public AttributeObject visitExpr(BinaryExpression binaryExpression) {
    AttributeObject left = binaryExpression.getLeft().accept(this);
    AttributeObject right = binaryExpression.getRight().accept(this);
    Resolver resolver = new Resolver();
    return switch (binaryExpression.getOperator()) {
      case "+" -> resolver.add(left, right);
      case "-" -> resolver.subtract(left, right);
      case "*" -> resolver.multiply(left, right);
      case "/" -> resolver.divide(left, right);
      default -> {
        String errorMsg = "Operator is not valid";
        errors.add(errorMsg);
        throw new RuntimeException(errorMsg);
      }
    };
  }

  @Override
  public AttributeObject visitExpr(LiteralExpression literalExpression) {
    return literalExpression.getValue();
  }

  @Override
  public AttributeObject visitExpr(UnaryExpression unaryExpression) {
    String value = unaryExpression.getValue();
    if (map.containsKey(value.substring(1))) {
      AttributeObject obj = map.get(value.substring(1));
      if (obj.typeIsCorrect(-(double) obj.getValue())) {
        map.put(value.substring(1), obj);
        return obj;
      } else {
        String errorMsg = "Cannot invert string values";
        errors.add(errorMsg);
        throw new RuntimeException(errorMsg);
      }
    } else {
      String errorMsg = "Value " + value + " does not exist";
      errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public AttributeObject visitExpr(VariableExpression variableExpression) {
    AttributeObject myObject = map.get(variableExpression.getVariableName());
    if (myObject.getValue() == null) {
      String errorMsg = "Variable " + variableExpression.getVariableName() + " was not initialized";
      errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
    return myObject;
  }

  @Override
  public AttributeObject visitExpr(ReadInputExpression readInputExpression) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("InsertType: ");
    String type = scanner.nextLine();
    System.out.println(readInputExpression.getMessage());
    String value = scanner.nextLine();
    scanner.close();
    return switch (type) {
      case "number" -> new NumberObj(Double.parseDouble(value), false);
      case "string" -> new StringObj(value, false);
      case "boolean" -> new BooleanObj(Boolean.parseBoolean(value), false);
      default -> throw new RuntimeException("Unsupported type");
    };
  }

  public HashMap<String, AttributeObject> getMap() {
    return map;
  }

  public List<String> getErrors() {
    return errors;
  }

  public List<String> getPrinted() {
    return printed;
  }
}
