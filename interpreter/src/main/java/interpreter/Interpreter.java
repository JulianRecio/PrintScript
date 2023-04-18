package interpreter;

import parser.AST;
import parser.VariableType;
import parser.expr.*;
import parser.node.*;

import java.util.HashMap;
import java.util.List;

public class Interpreter implements NodeVisitor, ExpressionVisitor {

    private final AST ast;
    private final HashMap<String, MyObject> map = new HashMap<>();

    public Interpreter(AST ast){
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
        if (map.containsKey(variable)){
            throw new RuntimeException("Variable " + variable + " is already declared");
        }
        else {
            MyObject classType;
            if (type == VariableType.NUMBER){
                classType = new NumberObj();
                map.put(variable, classType);
            }
            else if (type == VariableType.BOOLEAN){
                classType = new BooleanObj();
                map.put(variable, classType);
            }
            else {
                classType = new StringObj();
                map.put(variable, classType);
            }
            if (node.getInitializer() != null){
                MyObject result = node.getInitializer().accept(this);
                try {
                    classType.setValue(result.getValue());
                }
                catch (Exception e){
                    throw new RuntimeException("Mismatching types");
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
        if (map.containsKey(variable)){
            MyObject value = map.get(variable);
            try {
                value.setValue(result.getValue());
            }
            catch (Exception e){
                throw new RuntimeException("Mismatching types");
            }
            map.put(variable, result);
        }
        else {
            throw new RuntimeException("Variable does not exist");
        }
    }

    @Override
    public void visitNode(PrintNode node) {
        MyObject toPrint = node.getExpression().accept(this);
        if (toPrint.getValue() == null){
            throw new RuntimeException("Variable was not initialized");
        }
        else {
            System.out.println(toPrint.getValue().toString());
        }
    }

    @Override
    public void visitNode(IfNode node) {
        MyObject obj = node.getValue().accept(this);
        MyObject tmpObj = node.getValue().accept(this);
        try {
            tmpObj.setValue(true);
        }
        catch (Exception e){
            throw new RuntimeException("Expression inside if needs to be of type Boolean");
        }
        if ((boolean) obj.getValue()){
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
            default -> throw new RuntimeException("Operator is not valid");
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
            }
            catch (ClassCastException e){
                throw new RuntimeException("Cannot invert string values");

            }
            map.put(value.substring(1), obj);
            return obj;
        }
        else {
            throw new RuntimeException("Value " + value + " does not exist");
        }
    }

    @Override
    public MyObject visitExpr(VariableExpression variableExpression) {
        MyObject myObject = map.get(variableExpression.getVariableName());
        if (myObject.getValue() == null){
            throw new RuntimeException("Variable " + variableExpression.getVariableName() + " was not initialized");
        }
        return myObject;
    }

    public HashMap<String, MyObject> getMap() {
        return map;
    }
}

