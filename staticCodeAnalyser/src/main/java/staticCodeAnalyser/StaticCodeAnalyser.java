package staticCodeAnalyser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lexer.Lexer;
import parser.AST;
import parser.Parser;
import parser.node.Node;
import parser.node.NodeVisitor;
import staticCodeAnalyser.rules.CaseConventionRule;
import staticCodeAnalyser.rules.PrintConditionRule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StaticCodeAnalyser {

    private final Set<NodeVisitor> ruleSet;
    private final String caseConvention;
    private final boolean printlnCondition;

    public StaticCodeAnalyser(String configFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File fileObj = new File(configFile);

        Map<String, String> map;
        map =  mapper.readValue(
                    fileObj, new TypeReference<>() {
                });


        this.caseConvention = map.get("caseConvention");
        this.printlnCondition = Boolean.parseBoolean(map.get("printlnCondition"));

        ruleSet = selectRuleSet("1.0");//placeholder
    }

    private Set<NodeVisitor> selectRuleSet(String version) {
        switch (version){
            case "1.0" -> {
                Set<NodeVisitor> rules = new HashSet<>();
                rules.add(new CaseConventionRule(caseConvention));
                rules.add(new PrintConditionRule(printlnCondition));
                return rules;
            }
            case "1.1" -> {
                Set<NodeVisitor> rules = new HashSet<>();
                //agrego reglas 1.1
                return rules;
            }
        }
        return null;
    }

     public List<String> analyze(String source){
        Parser parser = new Parser(Lexer.tokenize(source));
        AST ast = parser.parse();

        List<Node> nodes = ast.getAst();
        List<String> messages = new ArrayList<>();

        for (Node node: nodes){
            try {
                for (NodeVisitor nodeVisitor :
                        ruleSet) {
                    node.accept(nodeVisitor);
                }
            } catch (Exception e){
                String message = e.getMessage();
                messages.add(message);
            }
        }

        for (String message :
                messages) {
            System.out.println(message);
        }
        return messages;
    }

    // public List<String> analyze(String source){
    //    Parser parser = new Parser(Lexer.tokenize(source));
    //    AST ast = parser.parse();
//
    //    List<Node> nodes = ast.getAst();
    //    List<String> messages = new ArrayList<>();
//
    //    for (Node node: nodes){
    //        try {
    //            node.accept(this);
    //        } catch (Exception e){
    //            String message = e.getMessage();
    //            messages.add(message);
    //        }
    //    }
//
    //    for (String message :
    //            messages) {
    //        System.out.println(message);
    //    }
    //    return messages;
    //}
//
    //@Override
    //public void visitNode(DeclarationNode node) {
    //    String variable = node.getVariableName();
//
    //    if (namingConvention.equals("snake case")){
    //        if (!variable.matches("[a-z]+(_[a-z]+)*")){
    //            throw new RuntimeException("Variable not written in snake case: " + node.getVariableName());
    //        }
    //    }else{
    //        if (!variable.matches("[a-z]+([A-Z][a-z]+)*")){
    //            throw new RuntimeException("Variable not written in camel case: " + node.getVariableName());
    //        }
    //    }
    //}
//
    //@Override
    //public void visitNode(AssignationNode node) {
//
    //}
//
    //@Override
    //public void visitNode(PrintNode node) {
    //    if (printlnCondition){
    //        Object printExpression = node.getExpression().accept(this);
//
    //        if (printExpression.equals(ExpressionType.BINARY) || printExpression.equals(ExpressionType.UNARY)){
    //            throw new RuntimeException("´println argument not valid: " + printExpression.toString());
    //        }
    //    }
    //}
//
//
    //@Override
    //public Object visitExpr(BinaryExpression binaryExpression) {
    //    return ExpressionType.BINARY;
    //}
//
    //@Override
    //public Object visitExpr(LiteralExpression literalExpression) {
    //    return ExpressionType.LITERAL;
    //}
//
    //@Override
    //public Object visitExpr(UnaryExpression unaryExpression) {
    //    return ExpressionType.UNARY;
    //}
//
    //@Override
    //public Object visitExpr(VariableExpression variableExpression) {
    //    return ExpressionType.VARIABLE;
    //}
//
    public String getCaseConvention() {
        return caseConvention;
    }

    public boolean isPrintlnCondition() {
        return printlnCondition;
    }
}