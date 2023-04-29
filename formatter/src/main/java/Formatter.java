import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import rule.Rule;
import rule.rules.*;
import token.Token;
import token.TokenType;

public class Formatter {

  public static String useFormatter(List<Token> tokens, Double version) {
    List<Rule> rules =
        getRulesFromConfig(readConfigFile("src\\main\\resources\\config.json"), version);
    return format(tokens, rules);
  }

  public static List<Rule> getRulesFromConfig(Map<String, String> ruleMap, Double version) {
    if (ruleMap == null) throw new RuntimeException("invalid path");

    List<Rule> rules = new ArrayList<>();

    if (ruleMap.containsKey("spaceBeforeColonInDeclaration")
        && ruleMap.get("spaceBeforeColonInDeclaration").equals("true"))
      rules.add(new SpaceBeforeColonRule());
    if (ruleMap.containsKey("spaceAfterColonInDeclaration")
        && ruleMap.get("spaceAfterColonInDeclaration").equals("true"))
      rules.add(new SpaceAfterColonRule());
    if (ruleMap.containsKey("spaceBeforeAndAfterEqualSignInAssignment")
        && ruleMap.get("spaceBeforeAndAfterEqualSignInAssignment").equals("true"))
      rules.add(new SpaceBeforeAndAfterEqualSignRule());
    if (ruleMap.containsKey("AmountOfLineBreaksBeforePrintLn"))
      rules.add(
          new LineBreakBeforePrintLnRule(
              Integer.parseInt(ruleMap.get("AmountOfLineBreaksBeforePrintLn"))));

    rules.add(new LineBreakAfterSemiColonRule());
    rules.add(new SpaceBeforeAndAfterOperatorRule());
    rules.add(new SpaceAfterKeywordRule());
    rules.add(new MaximumOf1SpaceBetweenTokensRule());

    if (version == 1.1) {
      // add 1.1 version rules
      if (ruleMap.containsKey("AmountOfIndentedInIfBlock"))
        rules.add(
            new IndentedIfBlockRule(Integer.parseInt(ruleMap.get("AmountOfIndentedInIfBlock"))));
      if (ruleMap.containsKey("IfKeyInSameLine") && ruleMap.get("IfKeyInSameLine").equals("true"))
        rules.add(new IfKeyOnSameLine());
    }

    return rules;
  }

  private static Map<String, String> readConfigFile(String path) {
    ObjectMapper mapper = new ObjectMapper();

    File fileObj = new File(path);

    try {
      return mapper.readValue(fileObj, new TypeReference<Map<String, String>>() {});
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String format(List<Token> tokens, List<Rule> rules) {
    StringBuilder formattedCode = new StringBuilder();
    List<Token> currentList = new ArrayList<>();
    for (Token currentToken : tokens) {
      if (currentToken.getType().equals(TokenType.END)
          || currentToken.getType().equals(TokenType.RIGHT_BRACKET)) {
        currentList.add(currentToken);
        // adds formatted line
        formattedCode.append(formatLine(currentList, rules));
        currentList = new ArrayList<>();
      } else {
        currentList.add(currentToken);
      }
    }
    System.out.println(formattedCode.toString());
    return formattedCode.toString();
  }

  private static String formatLine(List<Token> tokens, List<Rule> rules) {
    // apply all rules
    for (Rule rule : rules) {
      rule.applyRule(tokens);
    }
    return createLineFromTokenList(tokens);
  }

  private static String createLineFromTokenList(List<Token> tokens) {
    return tokens.stream().map(Token::getValue).collect(Collectors.joining());
  }
}
