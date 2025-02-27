package ast.node;

public interface NodeVisitor {

  void visitNode(DeclarationNode node);

  void visitNode(AssignationNode node);

  void visitNode(PrintNode node);

  void visitNode(IfNode node);
}
