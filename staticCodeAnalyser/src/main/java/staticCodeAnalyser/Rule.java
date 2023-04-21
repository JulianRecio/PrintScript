package staticCodeAnalyser;

import parser.node.AssignationNode;
import parser.node.DeclarationNode;
import parser.node.PrintNode;

public interface Rule {
    void applyRule(DeclarationNode node);
    void applyRule(AssignationNode node);
    void applyRule(PrintNode node);
}
