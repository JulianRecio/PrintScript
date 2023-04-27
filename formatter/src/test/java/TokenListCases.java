import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import token.Token;
import token.TokenType;

public class TokenListCases {

  public static List<Token> createSimpleDeclarationList() {
    return Arrays.asList(
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "x"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "number"),
        new Token(TokenType.END, ";"));
  }

  public static List<Token> createSimpleNumberAssigmentList() {
    return Arrays.asList(
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "x"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "number"),
        new Token(TokenType.EQUAL, "="),
        new Token(TokenType.NUMBER_VALUE, "3"),
        new Token(TokenType.END, ";"));
  }

  public static List<Token> createPrintComplexStringList() {
    return Arrays.asList(
        new Token(TokenType.PRINT, "printLn"),
        new Token(TokenType.LEFT_PARENTHESIS, "("),
        new Token(TokenType.STRING_VALUE, "\"Hello World\""),
        new Token(TokenType.RIGHT_PARENTHESIS, ")"),
        new Token(TokenType.END, ";"));
  }

  public static List<Token> createComplexEquationList() {
    return Arrays.asList(
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "x"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "number"),
        new Token(TokenType.EQUAL, "="),
        new Token(TokenType.NUMBER_VALUE, "3.6"),
        new Token(TokenType.OPERATOR, "+"),
        new Token(TokenType.NUMBER_VALUE, "3"),
        new Token(TokenType.OPERATOR, "*"),
        new Token(TokenType.NUMBER_VALUE, "3"),
        new Token(TokenType.END, ";"));
  }

  public static List<Token> createMultipleLinesList() {
    return Arrays.asList(
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "x"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "number"),
        new Token(TokenType.EQUAL, "="),
        new Token(TokenType.NUMBER_VALUE, "3.6"),
        new Token(TokenType.END, ";"),
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "y"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "string"),
        new Token(TokenType.EQUAL, "="),
        new Token(TokenType.STRING_VALUE, "\"Hello World\""),
        new Token(TokenType.END, ";"),
        new Token(TokenType.PRINT, "printLn"),
        new Token(TokenType.LEFT_PARENTHESIS, "("),
        new Token(TokenType.STRING_VALUE, "\"Hello World\""),
        new Token(TokenType.RIGHT_PARENTHESIS, ")"),
        new Token(TokenType.END, ";"));
  }

  public static List<Token> createIfElseSentenceList() {
    return Arrays.asList(
        new Token(TokenType.IF, "if"),
        new Token(TokenType.LEFT_PARENTHESIS, "("),
        new Token(TokenType.BOOLEAN_VALUE, "false"),
        new Token(TokenType.RIGHT_PARENTHESIS, ")"),
        new Token(TokenType.LEFT_BRACKET, "{"),
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "y"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "number"),
        new Token(TokenType.EQUAL, "="),
        new Token(TokenType.NUMBER_VALUE, "7"),
        new Token(TokenType.END, ";"),
        new Token(TokenType.RIGHT_BRACKET, "}"),
        new Token(TokenType.ELSE, "else"),
        new Token(TokenType.LEFT_BRACKET, "{"),
        new Token(TokenType.KEYWORD, "let"),
        new Token(TokenType.IDENTIFIER, "x"),
        new Token(TokenType.ALLOCATOR, ":"),
        new Token(TokenType.TYPE, "number"),
        new Token(TokenType.EQUAL, "="),
        new Token(TokenType.NUMBER_VALUE, "3"),
        new Token(TokenType.END, ";"),
        new Token(TokenType.RIGHT_BRACKET, "}"));
  }

  public static List<Token> createFullVersionList() {
    return Stream.concat(createMultipleLinesList().stream(), createIfElseSentenceList().stream())
        .collect(Collectors.toList());
  }
}
