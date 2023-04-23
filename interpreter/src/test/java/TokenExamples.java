import lexer.Token;
import lexer.TokenType;

public class TokenExamples {

  public static final Token LET_KEYWORD = new Token(TokenType.KEYWORD, "let");
  public static final Token CONST_KEYWORD = new Token(TokenType.KEYWORD, "const");
  public static final Token SIMPLE_IDENTIFIER = new Token(TokenType.IDENTIFIER, "x");
  public static final Token COMPLEX_IDENTIFIER = new Token(TokenType.IDENTIFIER, "variable");
  public static final Token UNARY_VALUE = new Token(TokenType.UNARY_VALUE, "-variableA");
  public static final Token ALLOCATOR = new Token(TokenType.ALLOCATOR, ":");
  public static final Token NUMBER_TYPE = new Token(TokenType.TYPE, "number");
  public static final Token STRING_TYPE = new Token(TokenType.TYPE, "string");
  public static final Token BOOLEAN_TYPE = new Token(TokenType.TYPE, "boolean");
  public static final Token EQUAL = new Token(TokenType.EQUAL, "=");
  public static final Token SIMPLE_NUMBER_VALUE = new Token(TokenType.NUMBER_VALUE, "3");
  public static final Token DECIMAL_NUMBER_VALUE = new Token(TokenType.NUMBER_VALUE, "3.6");
  public static final Token SIMPLE_STRING_VALUE = new Token(TokenType.STRING_VALUE, "\"a\"");
  public static final Token COMPLEX_STRING_VALUE =
      new Token(TokenType.STRING_VALUE, "\"Hola como\"");
  public static final Token BOOLEAN_VALUE = new Token(TokenType.BOOLEAN_VALUE, "true");
  public static final Token PLUS = new Token(TokenType.OPERATOR, "+");
  public static final Token MULTIPLICATION = new Token(TokenType.OPERATOR, "*");
  public static final Token PRINT = new Token(TokenType.PRINT, "printLn");
  public static final Token READ_INPUT = new Token(TokenType.READ_INPUT, "readInput");
  public static final Token LEFT_PAR = new Token(TokenType.LEFT_PARENTHESIS, "(");
  public static final Token RIGHT_PAR = new Token(TokenType.RIGHT_PARENTHESIS, ")");
  public static final Token LEFT_BRACKET = new Token(TokenType.LEFT_BRACKET, "{");
  public static final Token RIGHT_BRACKET = new Token(TokenType.RIGHT_BRACKET, "}");
  public static final Token IF = new Token(TokenType.IF, "if");
  public static final Token END = new Token(TokenType.END, ";");
}
