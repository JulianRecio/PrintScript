package parser;

import java.util.List;
import parser.node.Node;

public class AST {

  private List<Node> ast;

  public AST(List<Node> ast) {
    this.ast = ast;
  }

  public List<Node> getAst() {
    return ast;
  }
}
