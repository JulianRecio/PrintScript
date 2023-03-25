package parser.ast;

public class NumberAST extends AST{
    private String value;

    public NumberAST(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
