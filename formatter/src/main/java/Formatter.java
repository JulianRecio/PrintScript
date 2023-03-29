import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import rule.Rule;
import rule.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class Formatter {

    public static void useFormatter(String path) throws Exception {
        String input = "";
        List<Token> tokens = Lexer.tokenize(input);
        List<Rule> rules = getRulesFromConfig();
        format(tokens, rules);
    }

    private static List<Rule> getRulesFromConfig() throws Exception {
        Map<String, String> ruleMap = readConfig();
        if (ruleMap == null) throw new Exception();

        List<Rule> rules = new ArrayList<>();

        if (ruleMap.containsKey("spaceBeforeColonInDeclaration") && ruleMap.get("spaceBeforeColonInDeclaration").equals("true")) rules.add(new SpaceBeforeColonRule());
        if (ruleMap.containsKey("spaceAfterColonInDeclaration") && ruleMap.get("spaceAfterColonInDeclaration").equals("true")) rules.add(new SpaceAfterColonRule());
        if (ruleMap.containsKey("spaceBeforeAndAfterEqualSignInAssignment") && ruleMap.get("spaceBeforeAndAfterEqualSignInAssignment").equals("true")) rules.add(new SpaceBeforeAndAfterEqualSignRule());
        if (ruleMap.containsKey("AmountOfLineBreaksBeforePrintLn")) rules.add(new LineBreakBeforePrintLnRule(Integer.parseInt(ruleMap.get("AmountOfLineBreaksBeforePrintLn"))));

        rules.add(new LineBreakAfterSemiColonRule());
        rules.add(new SpaceBeforeAndAfterOperatorRule());
        rules.add(new MaximumOf1SpaceBetweenTokensRule());

        return rules;
    }

    private static Map<String, String> readConfig() {
        ObjectMapper mapper = new ObjectMapper();

        File fileObj = new File("formatter\\src\\main\\java\\config.json");

        try {
            return mapper.readValue(
                    fileObj, new TypeReference<Map<String, String>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        StringBuilder line = new StringBuilder();
        for (Token token:tokens){
            line.append(token.getValue());
        }
        return line.toString();
    }

    private static void printLines(List<String> lines){
        lines.forEach(System.out::println);
    }

}