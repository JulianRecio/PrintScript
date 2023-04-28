package ast.node;

public interface Node {

  void accept(NodeVisitor visitor);
}
