package lexer;

import com.google.common.collect.PeekingIterator;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import token.Token;
import token.TokenType;

public class Lexer {

  private Pattern KEYWORD_PATTERN;
  private Pattern IDENTIFIER_PATTERN;
  private Pattern ALLOCATOR_PATTER;
  private Pattern TYPE_PATTERN;
  private Pattern EQUAL_PATTERN;
  private Pattern NUMBER_PATTERN;
  private Pattern STRING_PATTERN;
  private Pattern BOOLEAN_PATTERN;
  private Pattern OPERATOR_PATTERN;
  private Pattern PRINT_PATTERN;
  private Pattern LEFT_PARENTHESIS_PATTERN;
  private Pattern RIGHT_PARENTHESIS_PATTERN;
  private Pattern END;
  private Pattern SPACE_PATTERN;
  private Pattern IF;
  private Pattern LEFT_BRACKET;
  private Pattern RIGHT_BRACKET;
  private Pattern ELSE;
  private Pattern READ_INPUT;

  private final PushbackInputStream inputStream;
  private final Double version;
  private final PeekingIterator<Token> tokenIterator;

  public Lexer(PushbackInputStream inputStream, Double version) {
    this.inputStream = inputStream;
    this.version = version;
    tokenIterator =
        new PeekingIterator<>() {
          List<Token> tokens = new ArrayList<>();

          @Override
          public boolean hasNext() {
            try {
              return inputStream.available() != 0 || tokens.size() != 0;
            } catch (IOException e) {
              return false;
            }
          }

          @Override
          public Token peek() {
            if (tokens.size() == 0) {
              try {
                tokens = returnToken();
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            }
            return tokens.get(0);
          }

          @Override
          public Token next() {
            if (tokens.size() == 0) {
              try {
                tokens = returnToken();
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            }
            Token token = tokens.get(0);
            tokens.remove(0);
            return token;
          }

          @Override
          public void remove() {
            if (tokens.size() != 0) {
              tokens.remove(0);
            } else throw new RuntimeException("No tokens");
          }
        };
  }

  public PeekingIterator<Token> getTokenIterator() {
    return tokenIterator;
  }

  public List<Token> returnToken() throws IOException {
    StringBuilder chunk = new StringBuilder();
    setVersionPatterns(version);
    int data = inputStream.read();
    while (data != -1) {
      char ch = (char) data;
      if (ch == ';' || ch == '}') {
        chunk.append(ch);
        while (true) {
          data = inputStream.read();
          if (data == -1) {
            break;
          }
          if (Character.isWhitespace((char) data)) {
            chunk.append((char) data);
          } else {
            inputStream.unread(data);
            break;
          }
        }
        return tokenizeChunk(chunk.toString(), version);
      } else {
        chunk.append(ch);
      }
      data = inputStream.read();
    }
    throw new RuntimeException("Invalid input");
  }

  private List<Token> tokenizeChunk(String input, Double version) {
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
          pos += str.length();
          if (map.get(matcher) == TokenType.STRING_VALUE) {
            // Remove the quotation marks from the string value
            str = str.substring(1, str.length() - 1);
          }
          tokens.add(new Token(map.get(matcher), str));
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

  private HashMap<Matcher, TokenType> createMap(String remainder) {
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

  private int addExtendedTokens(List<Token> tokens, int pos, String remainder) {
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

  private void setVersionPatterns(Double version) {
    ALLOCATOR_PATTER = Pattern.compile(":");
    EQUAL_PATTERN = Pattern.compile("=");
    NUMBER_PATTERN = Pattern.compile("(0|[1-9]\\d*)(\\.\\d+)?");
    STRING_PATTERN = Pattern.compile("\"([^\"]*)\"");
    OPERATOR_PATTERN = Pattern.compile("[+\\-*/]");
    PRINT_PATTERN = Pattern.compile("println\\b");
    LEFT_PARENTHESIS_PATTERN = Pattern.compile("\\(");
    RIGHT_PARENTHESIS_PATTERN = Pattern.compile("\\)");
    END = Pattern.compile(";");
    SPACE_PATTERN = Pattern.compile("\\s+");
    if (version == 1.0) {
      KEYWORD_PATTERN = Pattern.compile("let\\b");
      IDENTIFIER_PATTERN =
          Pattern.compile(
              "(?!println\\b)(?!number\\b)(?!string\\b)(?!let\\b)[a-zA-Z]+(_[a-zA-Z0-9]+)*");
      TYPE_PATTERN = Pattern.compile("number\\b|string\\b");
    } else if (version == 1.1) {
      KEYWORD_PATTERN = Pattern.compile("let\\b|const\\b");
      IDENTIFIER_PATTERN =
          Pattern.compile(
              "(?!println\\b)(?!readInput\\b)(?!number\\b)(?!string\\b)(?!boolean\\b)(?!let\\b)(?!const\\b)(?!true\\b)(?!false\\b)(?!if\\b)(?!else\\b)[a-zA-Z]+(_[a-zA-Z0-9]+)*");
      TYPE_PATTERN = Pattern.compile("number\\b|string\\b|boolean\\b");
      IF = Pattern.compile("if\\b");
      BOOLEAN_PATTERN = Pattern.compile("true\\b|false\\b");
      LEFT_BRACKET = Pattern.compile("\\{");
      RIGHT_BRACKET = Pattern.compile("}");
      ELSE = Pattern.compile("else\\b");
      READ_INPUT = Pattern.compile("readInput\\b");
    } else {
      throw new RuntimeException("Invalid version");
    }
  }
}
