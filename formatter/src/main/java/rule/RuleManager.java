package rule;

import rule.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleManager {

    private final List<Rule> declarationRules = new ArrayList<>();
    private final List<Rule> assignationRules = new ArrayList<>();
    private final List<Rule> printRules = new ArrayList<>();
    private final List<Rule> operationRules = new ArrayList<>();


    public RuleManager(Map<String, String> ruleMap){
        List<Rule> rules = createRuleList(ruleMap);

        for (Rule rule:rules){
            switch (rule.getRuleType()){
                case GENERAL -> {
                    declarationRules.add(rule);
                    assignationRules.add(rule);
                    printRules.add(rule);
                    operationRules.add(rule);
                }
                case ASSIGNATION -> {
                    assignationRules.add(rule);
                }
                case DECLARATION -> declarationRules.add(rule);
                case PRINT -> printRules.add(rule);
                case OPERATOR -> operationRules.add(rule);
                default -> {}
            }
        }
    }
    private List<Rule> createRuleList(Map<String, String> ruleMap){
        if (ruleMap == null) throw new RuntimeException("invalid path");

        List<Rule> allRules = new ArrayList<>();

        if (ruleMap.containsKey("spaceBeforeColonInDeclaration") && ruleMap.get("spaceBeforeColonInDeclaration").equals("true")) allRules.add(new SpaceBeforeColonRule());
        if (ruleMap.containsKey("spaceAfterColonInDeclaration") && ruleMap.get("spaceAfterColonInDeclaration").equals("true")) allRules.add(new SpaceAfterColonRule());
        if (ruleMap.containsKey("spaceBeforeAndAfterEqualSignInAssignment") && ruleMap.get("spaceBeforeAndAfterEqualSignInAssignment").equals("true")) allRules.add(new SpaceBeforeAndAfterEqualSignRule());
        if (ruleMap.containsKey("AmountOfLineBreaksBeforePrintLn")) allRules.add(new LineBreakBeforePrintLnRule(Integer.parseInt(ruleMap.get("AmountOfLineBreaksBeforePrintLn"))));

//        allRules.add(new LineBreakAfterSemiColonRule());
        allRules.add(new SpaceBeforeAndAfterOperatorRule());
        allRules.add(new SpaceAfterKeywordRule());
        allRules.add(new MaximumOf1SpaceBetweenTokensRule());
        return allRules;
    }

    public List<Rule> getDeclarationRules(){
        return declarationRules;
    }
    public List<Rule> getAssignationRules(){
        return assignationRules;
    }
    public List<Rule> getPrintRules(){
        return printRules;
    }
    public List<Rule> getOperatorRules(){
        return operationRules;
    }
}
