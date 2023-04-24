package staticCodeAnalyser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lexer.Lexer;
import parser.AST;
import parser.Parser;
import parser.node.Node;
import parser.node.NodeVisitor;
import staticCodeAnalyser.rules.CaseConventionRule;
import staticCodeAnalyser.rules.PrintLnConditionRule;
import staticCodeAnalyser.rules.ReadInputConditionRule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StaticCodeAnalyser {

    private final double version;
    private final Set<NodeVisitor> ruleSet;
    private final String caseConvention;
    private final boolean printLnCondition;
    private final boolean readInputCondition;

    public StaticCodeAnalyser(String configFile, double version) throws IOException {
        this.version = version;
        ObjectMapper mapper = new ObjectMapper();
        File fileObj = new File(configFile);

        Map<String, String> map;
        map =  mapper.readValue(
                    fileObj, new TypeReference<>() {
                });


        this.caseConvention = map.get("caseConvention");
        this.printLnCondition = Boolean.parseBoolean(map.get("printLnCondition"));
        this.readInputCondition = Boolean.parseBoolean(map.get("readInputCondition"));

        ruleSet = selectRuleSet(version);
    }

    private Set<NodeVisitor> selectRuleSet(Double version) {
        if (version == 1.0) {
            Set<NodeVisitor> rules = new HashSet<>();
            rules.add(new CaseConventionRule(caseConvention));
            rules.add(new PrintLnConditionRule(printLnCondition));
            return rules;
        } else if (version == 1.1) {
            Set<NodeVisitor> rules = new HashSet<>();
            rules.add(new CaseConventionRule(caseConvention));
            rules.add(new PrintLnConditionRule(printLnCondition));
            rules.add(new ReadInputConditionRule(readInputCondition));
            return rules;
        }
        return null;
    }

     public List<String> analyze(String source){
        Parser parser = new Parser(Lexer.tokenize(source,version));
        AST ast = parser.parse(version);

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

    public String getCaseConvention() {
        return caseConvention;
    }

    public boolean isPrintLnCondition() {
        return printLnCondition;
    }

    public boolean isReadInputCondition() {
        return readInputCondition;
    }
}