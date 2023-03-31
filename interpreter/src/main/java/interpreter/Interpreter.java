package interpreter;

import parser.AST;
import parser.VariableType;
import parser.expr.*;
import parser.node.*;

import java.util.HashMap;
import java.util.List;

public class Interpreter implements NodeVisitor, ExpressionVisitor {

    private final AST ast;
    private final HashMap<String, Object> map = new HashMap<>();

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
            throw new RuntimeException("Variable " + variable + " is already");
        }
        else {
            if (node.getInitializer() != null){
                Object result = node.getInitializer().accept(this);
                if (result instanceof Double && type == VariableType.NUMBER || result instanceof String && type == VariableType.STRING){
                    map.put(variable, result);
                }
                else {
                    throw new RuntimeException("Mismatching types");
                }
            }
            else {
                if (type == VariableType.NUMBER){
                    map.put(variable, new NumberObj());
                }
                else {
                    map.put(variable, new StringObj());
                }

            }
        }
    }

    @Override
    public void visitNode(AssignationNode node) {
        String variable = node.getVariable();
        Expression<?> right = node.getExpression();
        Object result = right.accept(this);
        if (map.containsKey(variable)){
            Object value = map.get(variable);
            if ((value instanceof Double || value instanceof NumberObj) && result instanceof Double || (value instanceof String || value instanceof StringObj) && result instanceof String){
                map.put(variable, result);
            }
            else {
                throw new RuntimeException("Mismatching types");
            }
        }
        else {
            throw new RuntimeException("Variable was not initialized");
        }
    }

    @Override
    public void visitNode(PrintNode node) {
        Object toPrint = node.getExpression().accept(this);
        System.out.println(toPrint.toString());
    }

    @Override
    public Object visitExpr(BinaryExpression binaryExpression) {
        Object left = binaryExpression.getLeft().accept(this);
        Object right = binaryExpression.getRight().accept(this);
        switch (binaryExpression.getOperator()) {
            case "+":
                if (left instanceof Double && right instanceof Double){
                    return (double) left + (double) right;
                }
                else if (left instanceof String && right instanceof String){
                    return (String) left + (String) right;
                }
                else {
                    throw new RuntimeException("Operands must be of same type");
                }
            case "-":
                checkIfNumberOperands(left, right);
                return (double) left - (double) right;
            case "*":
                checkIfNumberOperands(left, right);
                return (double) left * (double) right;
            case "/":
                checkIfNumberOperands(left, right);
                return (double) left / (double) right;
        }
        return null;
    }

    private void checkIfNumberOperands(Object left, Object right) {
        if (left instanceof Double && right instanceof Double){
            return;
        }
        else throw new RuntimeException("Operands must be numbers");
    }

    @Override
    public Object visitExpr(LiteralExpression literalExpression) {
        return literalExpression.getValue();
    }

    @Override
    public Object visitExpr(UnaryExpression unaryExpression) {
        String value = unaryExpression.getValue();
        if (map.containsKey(value.substring(1))) {
            Object obj = map.get(value.substring(1));
            if (obj instanceof Double) {
                map.put(value.substring(1), -(double) obj);
            }
        }
        return null;
    }

    @Override
    public Object visitExpr(VariableExpression variableExpression) {
        return map.get(variableExpression.getVariableName());
    }

    public HashMap<String, Object> getMap() {
        return map;
    }
}

