package lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import token.Token;
import token.TokenType;

public class Lexer {

  private static Pattern KEYWORD_PATTERN;
  private static Pattern IDENTIFIER_PATTERN;
  private static Pattern ALLOCATOR_PATTER;
  private static Pattern TYPE_PATTERN;
  private static Pattern EQUAL_PATTERN;
  private static Pattern NUMBER_PATTERN;
  private static Pattern STRING_PATTERN;
  private static Pattern BOOLEAN_PATTERN;
  private static Pattern OPERATOR_PATTERN;
  private static Pattern PRINT_PATTERN;
  private static Pattern LEFT_PARENTHESIS_PATTERN;
  private static Pattern RIGHT_PARENTHESIS_PATTERN;
  private static Pattern END;
  private static Pattern SPACE_PATTERN;
  private static Pattern IF;
  private static Pattern LEFT_BRACKET;
  private static Pattern RIGHT_BRACKET;
  private static Pattern ELSE;
  private static Pattern READ_INPUT;

  public static List<Token> tokenize(InputStream input, Double version) throws IOException {
    List<Token> tokens = new ArrayList<>();
    StringBuilder chunk = new StringBuilder();
    setVersionPatterns(version);
    int data = input.read();
    while (data != -1) {
      char ch = (char) data;
      if (ch == ';') {
        chunk.append(ch);
        tokens.addAll(tokenizeChunk(chunk.toString(), version));
        chunk = new StringBuilder();
      } else {
        chunk.append(ch);
      }
      data = input.read();
    }
    // If there is any remaining chunk, tokenize it
    if (chunk.length() > 0) {
      tokens.addAll(tokenizeChunk(chunk.toString(), version));
    }
    return tokens;
  }

  private static List<Token> tokenizeChunk(String input, Double version) {
    List<Token> tokens = new ArrayList<>();
    int pos = 0;
    while (pos < input.length()) {
      String remainder = input.substring(pos);
      HashMap<Matcher, TokenType> map = createMap(remainder);
      Matcher spaceMatcher = SPACE_PATTERN.matcher(remainder);
      boolean tmp = false;
      for (Matcher matcher : map.keySet()) {
        if (matcher.lookingAt()) {
          String str = matcher.group();
          tokens.add(new Token(map.get(matcher), str));
          pos += str.length();
          tmp = true;
          break;
        }
      }
      if (!tmp) {
        if (spaceMatcher.lookingAt()) {
          String space = spaceMatcher.group();
          pos += space.length();
        } else if (version == 1.1) {
          pos = addExtendedTokens(tokens, pos, remainder);
        } else {
          // If none of the patterns match, raise an error
          throw new RuntimeException("Invalid input at position " + pos);
        }
      }
    }
    return tokens;
  }

  private static HashMap<Matcher, TokenType> createMap(String remainder) {
    HashMap<Matcher, TokenType> map = new HashMap<>();
    map.put(KEYWORD_PATTERN.matcher(remainder), TokenType.KEYWORD);
    map.put(TYPE_PATTERN.matcher(remainder), TokenType.TYPE);
    map.put(IDENTIFIER_PATTERN.matcher(remainder), TokenType.IDENTIFIER);
    map.put(ALLOCATOR_PATTER.matcher(remainder), TokenType.ALLOCATOR);
    map.put(EQUAL_PATTERN.matcher(remainder), TokenType.EQUAL);
    map.put(NUMBER_PATTERN.matcher(remainder), TokenType.NUMBER_VALUE);
    map.put(STRING_PATTERN.matcher(remainder), TokenType.STRING_VALUE);
    map.put(OPERATOR_PATTERN.matcher(remainder), TokenType.OPERATOR);
    map.put(PRINT_PATTERN.matcher(remainder), TokenType.PRINT);
    map.put(LEFT_PARENTHESIS_PATTERN.matcher(remainder), TokenType.LEFT_PARENTHESIS);
    map.put(RIGHT_PARENTHESIS_PATTERN.matcher(remainder), TokenType.RIGHT_PARENTHESIS);
    map.put(END.matcher(remainder), TokenType.END);
    return map;
  }

  private static int addExtendedTokens(List<Token> tokens, int pos, String remainder) {
    HashMap<Matcher, TokenType> map = new HashMap<>();
    map.put(IF.matcher(remainder), TokenType.IF);
    map.put(BOOLEAN_PATTERN.matcher(remainder), TokenType.BOOLEAN_VALUE);
    map.put(LEFT_BRACKET.matcher(remainder), TokenType.LEFT_BRACKET);
    map.put(RIGHT_BRACKET.matcher(remainder), TokenType.RIGHT_BRACKET);
    map.put(ELSE.matcher(remainder), TokenType.ELSE);
    map.put(READ_INPUT.matcher(remainder), TokenType.READ_INPUT);
    boolean tmp = false;
    for (Matcher matcher : map.keySet()) {
      if (matcher.lookingAt()) {
        String str = matcher.group();
        tokens.add(new Token(map.get(matcher), str));
        pos += str.length();
        tmp = true;
        break;
      }
    }
    if (!tmp) {
      // If none of the patterns match, raise an error
      throw new RuntimeException("Invalid input at position " + pos);
    }
    return pos;
  }

  private static void setVersionPatterns(Double version) {
    ALLOCATOR_PATTER = Pattern.compile(":");
    EQUAL_PATTERN = Pattern.compile("=");
    NUMBER_PATTERN = Pattern.compile("-?(0|[1-9]\\d*)(\\.\\d+)?");
    STRING_PATTERN = Pattern.compile("\"[a-zA-Z][a-zA-Z0-9 ]*\"");
    OPERATOR_PATTERN = Pattern.compile("[+\\-*/]");
    PRINT_PATTERN = Pattern.compile("printLn");
    LEFT_PARENTHESIS_PATTERN = Pattern.compile("\\(");
    RIGHT_PARENTHESIS_PATTERN = Pattern.compile("\\)");
    END = Pattern.compile(";");
    SPACE_PATTERN = Pattern.compile("\\s+");
    if (version == 1.0) {
      KEYWORD_PATTERN = Pattern.compile("let");
      IDENTIFIER_PATTERN =
          Pattern.compile(
              "(?!printLn\\b)(?!number\\b)(?!string\\b)(?!let\\b)[a-zA-Z]+(_[a-zA-Z0-9]+)*");
      TYPE_PATTERN = Pattern.compile("number|string");
    } else if (version == 1.1) {
      KEYWORD_PATTERN = Pattern.compile("let|const");
      IDENTIFIER_PATTERN =
          Pattern.compile(
              "(?!printLn\\b)(?!readInput\\b)(?!number\\b)(?!string\\b)(?!boolean\\b)(?!let\\b)(?!const\\b)(?!true\\b)(?!false\\b)(?!if\\b)(?!else\\b)[a-zA-Z]+(_[a-zA-Z0-9]+)*");
      TYPE_PATTERN = Pattern.compile("number|string|boolean");
      IF = Pattern.compile("if");
      BOOLEAN_PATTERN = Pattern.compile("true|false");
      LEFT_BRACKET = Pattern.compile("\\{");
      RIGHT_BRACKET = Pattern.compile("}");
      ELSE = Pattern.compile("else");
      READ_INPUT = Pattern.compile("readInput");
    } else {
      throw new RuntimeException("Invalid version");
    }
  }
}
