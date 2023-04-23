import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import rule.Rule;
import rule.rules.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Collectors;

public class Formatter {


    public static List<String> useFormatter(String input) {
        List<Token> tokens = Lexer.tokenize(input);
        List<Rule> rules = getRulesFromConfig(readConfigFile("src\\main\\resources\\config.json"));
        return format(tokens, rules);
    }

    public static List<Rule> getRulesFromConfig(Map<String, String> ruleMap) {
        if (ruleMap == null) throw new RuntimeException("invalid path");

        List<Rule> rules = new ArrayList<>();

        if (ruleMap.containsKey("spaceBeforeColonInDeclaration") && ruleMap.get("spaceBeforeColonInDeclaration").equals("true")) rules.add(new SpaceBeforeColonRule());
        if (ruleMap.containsKey("spaceAfterColonInDeclaration") && ruleMap.get("spaceAfterColonInDeclaration").equals("true")) rules.add(new SpaceAfterColonRule());
        if (ruleMap.containsKey("spaceBeforeAndAfterEqualSignInAssignment") && ruleMap.get("spaceBeforeAndAfterEqualSignInAssignment").equals("true")) rules.add(new SpaceBeforeAndAfterEqualSignRule());
        if (ruleMap.containsKey("AmountOfLineBreaksBeforePrintLn")) rules.add(new LineBreakBeforePrintLnRule(Integer.parseInt(ruleMap.get("AmountOfLineBreaksBeforePrintLn"))));

//        rules.add(new LineBreakAfterSemiColonRule());
        rules.add(new SpaceBeforeAndAfterOperatorRule());
        rules.add(new SpaceAfterKeywordRule());
        rules.add(new MaximumOf1SpaceBetweenTokensRule());

        return rules;
    }

    private static Map<String, String> readConfigFile(String path) {
        ObjectMapper mapper = new ObjectMapper();

        File fileObj = new File(path);

        try {
            return mapper.readValue(
                    fileObj, new TypeReference<Map<String, String>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> format(List<Token> tokens, List<Rule> rules){
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
        return formattedCode;
    }
    private static String formatLine(List<Token> tokens, List<Rule> rules){
        //apply all rules
        for (Rule rule: rules){
            rule.applyRule(tokens);
        }
        return createLineFromTokenList(tokens);
    }
    private static String createLineFromTokenList(List<Token> tokens) {
        return tokens.stream()
                .map(Token::getValue)
                .collect(Collectors.joining());
    }

    private static void printLines(List<String> lines){
        lines.forEach(System.out::println);
    }
}