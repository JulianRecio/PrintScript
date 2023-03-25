package parser.ast;


public class BinaryAST extends AST{
    private String operator;
    private AST left;
    private AST right;

    public BinaryAST(String operator, AST left, AST right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
