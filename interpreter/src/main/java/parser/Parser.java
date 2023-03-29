package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.*;
import parser.ast.*;

public class Parser {

    private List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.pos = 0;
    }

    public AST parse() throws Exception {
        List<Node> ast = new ArrayList<>();
        while (pos < tokens.size()) {
            if (tokens.get(pos).getType() == TokenType.KEYWORD){
                Node keyword = new SimpleNode(tokens.get(pos).getValue());
                pos++;
                Node declaration = new BinaryNode("declaration", keyword, allocate());
                if (tokens.get(pos).getType() == TokenType.END){
                    pos ++;
                    ast.add(declaration);
                }
                else if (tokens.get(pos).getType() == TokenType.EQUAL) {
                    Node equal = new BinaryNode(tokens.get(pos).getValue(), declaration, expression());
                    ast.add(equal);
                }
                else {
                    throw new RuntimeException("= or ; tokens were expected but not found");
                }
            }
            else if (tokens.get(pos).getType() == TokenType.IDENTIFIER){
                Node expression = expression();
                ast.add(expression);
            }
            else if (tokens.get(pos).getType() == TokenType.PRINT) {
                pos++;
                if (tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS) {
                    pos++;
                    Node expression = expression();
                    if (tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS) {
                        Node print = new PrintNode(expression);
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

    public Node allocate() {
        if (tokens.get(pos).getType() == TokenType.IDENTIFIER){
            Node identifier = new SimpleNode(tokens.get(pos).getValue());
            pos ++;
            if (tokens.get(pos).getType() == TokenType.ALLOCATOR){
                pos ++;
                if (tokens.get(pos).getType() == TokenType.TYPE) {
                    Node type = new SimpleNode(tokens.get(pos).getValue());
                    Node allocator = new BinaryNode(":", identifier, type);
                    pos ++;
                    return allocator;
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

    public Node expression(){
        Node left = term();
        while (tokens.get(pos).getType() == TokenType.END){
            Token token = tokens.get(pos);
            if (token.getType() == TokenType.OPERATOR && "+-".contains(token.getValue())) {
                pos++;
                Node right = factor();
                left = new BinaryNode(token.getValue(), left, right);
            }
            else {
                break;
            }
        }
        return left;
    }

    public Node term(){
        Node left = factor();
        while (tokens.get(pos).getType() == TokenType.END){
            Token token = tokens.get(pos);
            if (token.getType() == TokenType.OPERATOR && "/*".contains(token.getValue())) {
                pos++;
                Node right = factor();
                left = new BinaryNode(token.getValue(), left, right);
            }
            else {
                break;
            }
        }
        return left;
    }

    public Node factor(){
        if (tokens.get(pos).getType() == TokenType.NUMBER_VALUE) {
            Node numberNode = new NumberNode(tokens.get(pos).getValue());
            pos ++;
            return numberNode;
        }
        else if (tokens.get(pos).getType() == TokenType.STRING_VALUE) {
            Node stringNode = new SimpleNode(tokens.get(pos).getValue());
            pos++;
            return stringNode;
        }
        else if (tokens.get(pos).getType() == TokenType.IDENTIFIER) {
            Node identifier = new SimpleNode(tokens.get(pos).getValue());
            pos++;
            return identifier;
        }
        else {
            throw new RuntimeException("Expected number or string value");
        }
    }
}
