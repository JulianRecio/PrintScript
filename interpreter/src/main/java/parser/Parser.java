package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.*;
import parser.expr.*;
import parser.node.*;
import org.javatuples.Pair;

public class Parser {

    private final List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.pos = 0;
    }

    public AST parse() {
        List<Node> ast = new ArrayList<>();
        while (pos < tokens.size()) {
            if (tokens.get(pos).getType() == TokenType.KEYWORD){
                pos++;
                Pair<String, VariableType> declaration = setType();
                if (tokens.get(pos).getType() == TokenType.END){
                    pos ++;
                    Node declarationNode = new DeclarationNode(declaration.getValue0(), declaration.getValue1(), null);
                    ast.add(declarationNode);
                }
                else if (tokens.get(pos).getType() == TokenType.EQUAL) {
                    Node declarationNode = new DeclarationNode(declaration.getValue0(), declaration.getValue1(), expression());
                    ast.add(declarationNode);
                }
                else {
                    throw new RuntimeException("= or ; tokens were expected but not found");
                }
            }
            else if (tokens.get(pos).getType() == TokenType.IDENTIFIER){
                Expression<Object> left = new VariableExpression(tokens.get(pos).getValue());
                pos ++;
                Expression<Object> right = expression();
                Node assignationNode = new AssignationNode(left, right);
                ast.add(assignationNode);
            }
            else if (tokens.get(pos).getType() == TokenType.PRINT) {
                pos++;
                if (tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS) {
                    pos++;
                    Expression<Object> expression = expression();
                    if (tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS) {
                        Node print = new PrintNode(expression);
                        ast.add(print);
                    }
                    else {
                        throw new RuntimeException("')' expected but not found");
                    }
                }
                else {
                    throw new RuntimeException("'(' expected but not found");
                }
            }
            else {
                throw new RuntimeException("Line starts with incorrect token");
            }
        }
       return new AST(ast);
    }

    private Pair<String, VariableType> setType() {
        if (tokens.get(pos).getType() == TokenType.IDENTIFIER){
            String identifier = tokens.get(pos).getValue();
            pos ++;
            if (tokens.get(pos).getType() == TokenType.ALLOCATOR){
                pos ++;
                if (tokens.get(pos).getType() == TokenType.TYPE) {
                    String type = tokens.get(pos).getValue();
                    pos++;
                    if (type.equalsIgnoreCase("number")){
                        return new Pair<>(identifier, VariableType.INTEGER);
                    }
                    else {
                        return new Pair<>(identifier, VariableType.STRING);
                    }
                }
                else {
                    throw new RuntimeException("There is no type after allocation");
                }
            }
            else {
                throw new RuntimeException("There is no : after variable");
            }
        }
        else {
            throw new RuntimeException("There is no variable name after keyword");
        }
    }

    private Expression<Object> expression(){
        Expression<Object> left = term();
        while (tokens.get(pos).getType() != TokenType.END){
            Token token = tokens.get(pos);
            if (token.getType() == TokenType.OPERATOR && "+-".contains(token.getValue())) {
                pos++;
                Expression<Object> right = term();
                left = new BinaryExpression(token.getValue(), left, right);
            }
            else {
                break;
            }
        }
        return left;
    }

    private Expression<Object> term(){
        Expression<Object> left = factor();
        while (tokens.get(pos).getType() != TokenType.END){
            Token token = tokens.get(pos);
            if (token.getType() == TokenType.OPERATOR && "/*".contains(token.getValue())) {
                pos++;
                Expression<Object> right = factor();
                left = new BinaryExpression(token.getValue(), left, right);
            }
            else {
                break;
            }
        }
        return left;
    }

    private Expression<Object> factor(){
        if (tokens.get(pos).getType() == TokenType.NUMBER_VALUE) {
            Expression<Object> numberExpr = new LiteralExpression(Integer.parseInt(tokens.get(pos).getValue()));
            pos ++;
            return numberExpr;
        }
        else if (tokens.get(pos).getType() == TokenType.STRING_VALUE) {
            Expression<Object> stringExpr = new LiteralExpression(tokens.get(pos).getValue());
            pos++;
            return stringExpr;
        }
        else if (tokens.get(pos).getType() == TokenType.IDENTIFIER) {
            Expression<Object> identifierExpr = new VariableExpression(tokens.get(pos).getValue());
            pos++;
            return identifierExpr;
        }
        else {
            throw new RuntimeException("Expected number, string value or a variable in expression");
        }
    }
}
