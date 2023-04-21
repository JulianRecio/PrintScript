package staticCodeAnalyser.rules;

import parser.node.AssignationNode;
import parser.node.DeclarationNode;
import parser.node.NodeVisitor;
import parser.node.PrintNode;

public class caseConventionRule implements NodeVisitor {

    private final String caseConvention;

    public caseConventionRule(String caseConvention) {
        this.caseConvention = caseConvention;
    }


    @Override
    public void visitNode(DeclarationNode node) {

        String variable = node.getVariableName();

        if (caseConvention.equals("snake case")){
            if (!variable.matches("[a-z]+(_[a-z]+)*")){
                throw new RuntimeException("Variable not written in snake case: " + node.getVariableName());
            }
        }

        if(caseConvention.equals("camel case")){
            if (!variable.matches("[a-z]+([A-Z][a-z]+)*")){
                throw new RuntimeException("Variable not written in camel case: " + node.getVariableName());
            }
        }
    }

    @Override
    public void visitNode(AssignationNode node) {

    }

    @Override
    public void visitNode(PrintNode node) {

    }
}
