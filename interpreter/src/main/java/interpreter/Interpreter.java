package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import parser.AST;
import parser.VariableType;
import parser.expr.*;
import parser.node.*;

public class Interpreter implements NodeVisitor, ExpressionVisitor {

  private final AST ast;
  private final HashMap<String, MyObject> map = new HashMap<>();
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
      MyObject classType;
      if (type == VariableType.NUMBER) {
        classType = new NumberObj();
        map.put(variable, classType);
      } else if (type == VariableType.BOOLEAN) {
        classType = new BooleanObj();
        map.put(variable, classType);
      } else {
        classType = new StringObj();
        map.put(variable, classType);
      }
      if (node.getInitializer() != null) {
        MyObject result = node.getInitializer().accept(this);
        try {
          classType.setValue(result.getValue());
        } catch (Exception e) {
            String errorMsg = "Mismatching types";
            errors.add(errorMsg);
          throw new RuntimeException(errorMsg);
        }
        map.put(variable, result);
      }
    }
  }

  @Override
  public void visitNode(AssignationNode node) {
    String variable = node.getVariable();
    Expression<MyObject> right = node.getExpression();
    MyObject result = right.accept(this);
    if (map.containsKey(variable)) {
      MyObject value = map.get(variable);
      try {
        value.setValue(result.getValue());
      } catch (Exception e) {
          String errorMsg = "Mismatching types";
          errors.add(errorMsg);
        throw new RuntimeException(errorMsg);
      }
      map.put(variable, result);
    } else {
        String errorMsg = "Variable does not exist";
        errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public void visitNode(PrintNode node) {
    MyObject toPrint = node.getExpression().accept(this);
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
    MyObject obj = node.getValue().accept(this);
    MyObject tmpObj = node.getValue().accept(this);
    try {
      tmpObj.setValue(true);
    } catch (Exception e) {
        String errorMsg = "Expression inside if needs to be of type Boolean";
        errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
    if ((boolean) obj.getValue()) {
      List<Node> tmpAST = node.getIfAST().getAst();
      for (int i = 0; i < tmpAST.size(); i++) {
        tmpAST.get(i).accept(this);
      }
    } else if (node.getElseAST() != null) {
      List<Node> tmpAST = node.getElseAST().getAst();
      for (int i = 0; i < tmpAST.size(); i++) {
        tmpAST.get(i).accept(this);
      }
    }
  }

  @Override
  public MyObject visitExpr(BinaryExpression binaryExpression) {
    MyObject left = binaryExpression.getLeft().accept(this);
    MyObject right = binaryExpression.getRight().accept(this);
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
  public MyObject visitExpr(LiteralExpression literalExpression) {
    return literalExpression.getValue();
  }

  @Override
  public MyObject visitExpr(UnaryExpression unaryExpression) {
    String value = unaryExpression.getValue();
    if (map.containsKey(value.substring(1))) {
      MyObject obj = map.get(value.substring(1));
      try {
        obj.setValue(-(double) obj.getValue());
      } catch (ClassCastException e) {
          String errorMsg = "Cannot invert string values";
          errors.add(errorMsg);
        throw new RuntimeException(errorMsg);
      }
      map.put(value.substring(1), obj);
      return obj;
    } else {
        String errorMsg = "Value " + value + " does not exist";
        errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public MyObject visitExpr(VariableExpression variableExpression) {
    MyObject myObject = map.get(variableExpression.getVariableName());
    if (myObject.getValue() == null) {
        String errorMsg = "Variable " + variableExpression.getVariableName() + " was not initialized";
        errors.add(errorMsg);
      throw new RuntimeException(errorMsg);
    }
    return myObject;
  }

  public HashMap<String, MyObject> getMap() {
    return map;
  }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getPrinted() {
        return printed;
    }
}
