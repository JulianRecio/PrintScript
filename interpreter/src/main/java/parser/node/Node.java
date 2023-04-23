package parser.node;

public interface Node {

  void accept(NodeVisitor visitor);
}
