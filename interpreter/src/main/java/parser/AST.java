package parser;

import parser.node.Node;

import java.util.List;

public class AST {

    private List<Node> ast;

    public AST(List<Node> ast) {
        this.ast = ast;
    }

    public List<Node> getAst() {
        return ast;
    }
}
