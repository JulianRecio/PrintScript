package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.*;
import parser.ast.AST;
import parser.ast.BinaryNode;
import parser.ast.Node;
import parser.ast.SimpleNode;

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
                }
            }
            else if (tokens.get(pos).getType() == TokenType.IDENTIFIER){

            }
        }
       return new AST(ast);
    }

    public Node allocate() throws Exception {
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
                    throw new Exception("There is no type after allocation");
                }
            }
            else {
                throw new Exception("There is no : after variable");
            }
        }
        else {
            throw new Exception("There is no variable name after keyword");
        }
    }

    public Node expression(){
        return null;
    }
}
