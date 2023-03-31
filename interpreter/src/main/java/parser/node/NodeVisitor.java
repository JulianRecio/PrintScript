package parser.node;


public interface NodeVisitor {

    void visit(DeclarationNode node);
    void visit(AssignationNode node);
    void visit(PrintNode node);
}
