package formatter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.PeekingIterator;
import formatter.rule.Rule;
import formatter.rule.rules.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import token.Token;
import token.TokenType;

public class Formatter {

  public static String useFormatter(PeekingIterator<Token> tokenIterator, Double version) {
    List<Rule> rules =
        getRulesFromConfig(readConfigFile("src\\main\\resources\\config.json"), version);
    return format(tokenIterator, rules);
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
    if (ruleMap.containsKey("AmountOfLineBreaksBeforePrintLn")
        && Integer.parseInt(ruleMap.get("AmountOfLineBreaksBeforePrintLn")) != -1)
      rules.add(
          new LineBreakBeforePrintLnRule(
              Integer.parseInt(ruleMap.get("AmountOfLineBreaksBeforePrintLn"))));

    rules.add(new LineBreakAfterSemiColonRule());
    rules.add(new SpaceBeforeAndAfterOperatorRule());
    rules.add(new SpaceAfterKeywordRule());
    rules.add(new MaximumOf1SpaceBetweenTokensRule());

    if (version == 1.1) {
      // add 1.1 version rules
      if (ruleMap.containsKey("AmountOfIndentedInIfBlock")
          && Integer.parseInt(ruleMap.get("AmountOfIndentedInIfBlock")) != -1)
        rules.add(
            new IndentedIfBlockRule(Integer.parseInt(ruleMap.get("AmountOfIndentedInIfBlock"))));
      if (ruleMap.containsKey("IfKeyInSameLine") && ruleMap.get("IfKeyInSameLine").equals("true"))
        rules.add(new IfKeyOnSameLine());
    }

    return rules;
  }

  public static Map<String, String> readConfigFile(String path) {
    ObjectMapper mapper = new ObjectMapper();

    File fileObj = new File(path);

    try {
      return mapper.readValue(fileObj, new TypeReference<Map<String, String>>() {});
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String format(PeekingIterator<Token> tokenIterator, List<Rule> rules) {
    StringBuilder formattedCode = new StringBuilder();
    List<Token> currentList = new ArrayList<>();
    while (tokenIterator.hasNext()) {
      Token token = tokenIterator.next();
      if (token.getType().equals(TokenType.END)
          || token.getType().equals(TokenType.RIGHT_BRACKET)) {
        currentList.add(token);
        // adds formatted line
        formattedCode.append(formatLine(currentList, rules));
        currentList = new ArrayList<>();
      } else {
        currentList.add(token);
      }
    }
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
