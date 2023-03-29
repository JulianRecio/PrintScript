package parser.ast;

public class NumberNode extends Node {

    private String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
