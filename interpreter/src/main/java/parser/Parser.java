package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.*;

public class Parser {

    private List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    public Node parse() {
        return expression();
    }

    private Node expression() {
        Node left = term();
        while (pos < tokens.size()) {
            Token token = tokens.get(pos);
            if (token.getType() == TokenType.OPERATOR && "+-".contains(token.getValue())) {
                pos++;
                Node right = term();
                left = new BinaryOperatorNode(token.getValue(), left, right);
            } else {
                break;
            }
        }
        return left;
    }

    private Node term() {
        Node left = factor();
        while (pos < tokens.size()) {
            Token token = tokens.get(pos);
            if (token.getType() == TokenType.OPERATOR && "*/".contains(token.getValue())) {
                pos++;
                Node right = factor();
                left = new BinaryOperatorNode(token.getValue(), left, right);
            } else {
                break;
            }
        }
        return left;
    }

    private Node factor() {
        Token token = tokens.get(pos);
        if (token.getType() == TokenType.NUMBER) {
            pos++;
            return new NumberNode(token.getValue());
        } else if (token.getType() == TokenType.IDENTIFIER) {
            pos++;
            if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.PUNCTUATION && tokens.get(pos).getValue().equals("(")) {
                pos++;
                Node expr = expression();
                if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.PUNCTUATION && tokens.get(pos).getValue().equals(")")) {
                    pos++;
                    return new FunctionCallNode(token.getValue(), expr);
                } else {
                    throw new RuntimeException("Expected ')' at position " + pos);
                }
            } else {
                return new VariableNode(token.getValue());
            }
        } else if (pos < tokens.size() && token.getType() == TokenType.PUNCTUATION && token.getValue().equals("(")) {
            pos++;
            Node expr = expression();
            if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.PUNCTUATION && tokens.get(pos).getValue().equals(")")) {
                pos++;
                return expr;
            } else {
                throw new RuntimeException("Expected ')' at position " + pos);
            }
        } else {
            throw new RuntimeException("Unexpected token at position " + pos);
        }
    }
}

abstract class Node {}

class NumberNode extends Node {
    private String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

class VariableNode extends Node {
    private String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class BinaryOperatorNode extends Node {
    private String operator;
    private Node left;
    private Node right;

    public BinaryOperatorNode(String operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
