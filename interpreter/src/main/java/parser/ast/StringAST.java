package parser.ast;

public class StringAST extends AST{
    private String value;

    public StringAST(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
