import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import rule.Rule;
import rule.rules.LineBreakAfterSemiColonRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formatter {

    public static void useFormatter(String path){
        String input = "";
        List<Token> tokens = Lexer.tokenize(input);
        List<Rule> rules = getRulesFromConfig();
        format(tokens, rules);
    }

    private static List<Rule> getRulesFromConfig() {
        Map<String, String> ruleMap = new HashMap<>();

        List<Rule> rules = new ArrayList<>();
        rules.add(new LineBreakAfterSemiColonRule());
        return rules;
    }

    public static void format(List<Token> tokens, List<Rule> rules){
        List<String> formattedCode = new ArrayList<>();
        List<Token> currentList = new ArrayList<>();
        for (Token currentToken : tokens){
            if (currentToken.getType().equals(TokenType.END)){
                currentList.add(currentToken);
                //adds formatted line
                formattedCode.add(formatLine(currentList, rules));
                currentList = new ArrayList<>();
            }
            else{
                currentList.add(currentToken);
            }
        }
        printLines(formattedCode);
    }
    private static String formatLine(List<Token> tokens, List<Rule> rules){
        for (Rule rule: rules){
            rule.applyRule(tokens);
        }
        return createLineFromTokens(tokens);
    }

    private static String createLineFromTokens(List<Token> tokens) {

    }

    private static void printLines(List<String> lines){
        lines.forEach(System.out::println);
    }

}