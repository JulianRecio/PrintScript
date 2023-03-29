package parser.ast;


public class BinaryNode extends Node {
    private String operator;
    private Node left;
    private Node right;

    public BinaryNode(String operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
