package interpreter;

import ast.VariableType;
import ast.expr.*;
import ast.node.*;
import ast.obj.*;
import java.util.*;
import java.util.function.Consumer;

public class Interpreter implements NodeVisitor, ExpressionVisitor<AttributeObject> {

  private final HashMap<String, AttributeObject> map = new HashMap<>();
  private final Iterator<Node> nodeIterator;
  private final Consumer<String> out;

  public Interpreter(Iterator<Node> nodeIterator) {
    this.nodeIterator = nodeIterator;
    this.out = System.out::println;
  }

  public Interpreter(Iterator<Node> nodeIterator, Consumer<String> out) {
    this.nodeIterator = nodeIterator;
    this.out = out;
  }

  public void interpret() {
    Exception error = null;
    while (error == null) {
      try {
        Node node = nodeIterator.next();
        node.accept(this);
      } catch (Exception e) {
        if (e.getMessage().equalsIgnoreCase("There are no more tokens")) {
          error = e;
        } else throw e;
      }
    }
  }

  @Override
  public void visitNode(DeclarationNode node) {
    String variable = node.getVariableName();
    VariableType type = node.getType();
    if (map.containsKey(variable)) {
      String errorMsg = "Variable " + variable + " is already declared";
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
          throw new RuntimeException(errorMsg);
        }
      } else throw new RuntimeException("Variable is const, cannot change value");
    } else {
      String errorMsg = "Variable does not exist";
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public void visitNode(PrintNode node) {
    AttributeObject toPrint = node.getExpression().accept(this);
    if (toPrint.getValue() == null) {
      String errorMsg = "Variable was not initialized";
      throw new RuntimeException(errorMsg);
    } else {
      print(toPrint.getValue().toString());
    }
  }

  @Override
  public void visitNode(IfNode node) {
    AttributeObject obj = node.getValue().accept(this);
    if (node.getValue().accept(this) instanceof BooleanObj) {
      if ((boolean) obj.getValue()) {
        List<Node> tmpAST = node.getIfAST();
        for (Node value : tmpAST) {
          value.accept(this);
        }
      } else if (node.getElseAST() != null) {
        List<Node> tmpAST = node.getElseAST();
        for (Node value : tmpAST) {
          value.accept(this);
        }
      }
    } else {
      String errorMsg = "Expression inside if needs to be of type Boolean";
      throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public AttributeObject visitExpr(BinaryExpression binaryExpression) {
    AttributeObject left = binaryExpression.getLeft().accept(this);
    AttributeObject right = binaryExpression.getRight().accept(this);
    Resolver resolver = new Resolver();
    switch (binaryExpression.getOperator()) {
      case "+":
        return resolver.add(left, right);
      case "-":
        return resolver.subtract(left, right);
      case "*":
        return resolver.multiply(left, right);
      case "/":
        return resolver.divide(left, right);
      default:
        String errorMsg = "Operator is not valid";
        throw new RuntimeException(errorMsg);
    }
  }

  @Override
  public AttributeObject visitExpr(LiteralExpression literalExpression) {
    return literalExpression.getValue();
  }

  @Override
  public AttributeObject visitExpr(VariableExpression variableExpression) {
    AttributeObject myObject = map.get(variableExpression.getVariableName());
    if (myObject.getValue() == null) {
      String errorMsg = "Variable " + variableExpression.getVariableName() + " was not initialized";
      throw new RuntimeException(errorMsg);
    }
    return myObject;
  }

  @Override
  public AttributeObject visitExpr(ReadInputExpression readInputExpression) {
    Scanner scanner = new Scanner(System.in);
    String msg = readInputExpression.getMessage();
    print(msg);
    String value = scanner.nextLine();
    scanner.close();
    switch (readInputExpression.getVariableType()) {
      case NUMBER:
        return new NumberObj(Integer.parseInt(value), false);
      case STRING:
        return new StringObj(value, false);
      case BOOLEAN:
        return new BooleanObj(Boolean.parseBoolean(value), false);
      default:
        throw new RuntimeException("Unsupported type");
    }
  }

  public HashMap<String, AttributeObject> getMap() {
    return map;
  }

  private void print(String str) {
    if (out != null) out.accept(str);
  }
}
