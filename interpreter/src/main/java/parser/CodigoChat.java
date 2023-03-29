package parser;
import java.util.List;
import lexer.*;

public class CodigoChat {

    /*public class Parser {

        private List<Token> tokens;
        private int pos;

        public Parser(List<Token> tokens) {
            this.tokens = tokens;
            this.pos = 0;
        }

        public Node parse() {      //Hace el AST
            return expression();
        }

        private Node expression() {    //Primero revisa si es + o -
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

        private Node term() {       //Segundo revisa si es * o /
            Node left = factor();
            while (pos < tokens.size()) {
                Token token = tokens.get(pos);
                if (token.getType() == TokenType.OPERATOR && "*//*".contains(token.getValue())) {
      /*              pos++;
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
                if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.LEFT_PARENTHESIS && tokens.get(pos).getValue().equals("(")) {
                    pos++;
                    Node expr = expression();
                    if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS && tokens.get(pos).getValue().equals(")")) {
                        pos++;
                        return new FunctionCallNode(token.getValue(), expr);
                    } else {
                        throw new RuntimeException("Expected ')' at position " + pos);
                    }
                } else {
                    return new StringNode(token.getValue());
                }
            } else if (pos < tokens.size() && token.getType() == TokenType.LEFT_PARENTHESIS && token.getValue().equals("(")) {
                pos++;
                Node expr = expression();
                if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.RIGHT_PARENTHESIS && tokens.get(pos).getValue().equals(")")) {
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

    abstract class Node {

    }

    class NumberNode extends Node {
        private String value;

        public NumberNode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class StringNode extends Node {
        private String value;

        public StringNode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
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


    /*
    Certainly, I'd be happy to explain the code!

The code I provided is an example implementation of a simple parser for arithmetic expressions in Java. The parser takes a list of tokens generated by a lexer (not included in this code) as input and outputs an abstract syntax tree (AST) representing the expression.

Here's a breakdown of the code:

Parser class: This class is the main parser class that takes in a list of tokens and provides a parse() method to build an AST from the tokens.

Node abstract class: This is the base class for all nodes in the AST. It has no methods or properties of its own, but is used to define the basic structure of the AST.

NumberNode class: This class represents a node in the AST that contains a numerical value. It has a single property value that stores the value of the node.

StringNode class: This class represents a node in the AST that contains a variable name. It has a single property name that stores the name of the variable.

BinaryOperatorNode class: This class represents a node in the AST that contains a binary operator. It has three properties: operator (the operator symbol), left (the left operand), and right (the right operand).

parse() method: This method is the entry point to the parser. It calls the expression() method to parse an expression and return the corresponding AST.

expression() method: This method parses an expression by calling the term() method to parse the left operand, then repeatedly checks for an operator token and calls term() again to parse the right operand. It constructs a BinaryOperatorNode with the operator symbol and left and right operands, and returns the resulting node.

term() method: This method parses a term by calling the factor() method to parse the left operand, then repeatedly checks for a multiplication or division operator token and calls factor() again to parse the right operand. It constructs a BinaryOperatorNode with the operator symbol and left and right operands, and returns the resulting node.

factor() method: This method parses a factor, which can be a number, variable, or expression in parentheses. If the factor is a variable, it checks for a left parenthesis to determine if the variable is actually a function call, and calls expression() to parse the function arguments. If the factor is an expression in parentheses, it calls expression() recursively to parse the enclosed expression. It constructs a NumberNode, StringNode, or BinaryOperatorNode, depending on the type of factor, and returns the resulting node.

Overall, this parser is a simple example of how a parser can be implemented in Java. It is not a complete implementation and may not handle all possible cases, but it demonstrates the basic structure and principles of a parser.
    */
}
