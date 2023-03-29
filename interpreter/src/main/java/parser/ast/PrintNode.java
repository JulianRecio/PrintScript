package parser.ast;

public class PrintNode extends Node{

    private Node printExpression;

    public PrintNode(Node node){
        this.printExpression = node;
    }
}
