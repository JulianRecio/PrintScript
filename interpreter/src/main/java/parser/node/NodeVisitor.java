package parser.node;


public interface NodeVisitor {

    void visitNode(DeclarationNode node);
    void visitNode(AssignationNode node);
    void visitNode(PrintNode node);
}
