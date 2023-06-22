import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.Arrays;
import token.Token;
import token.TokenType;

public class TokenListCases {

  public static PeekingIterator<Token> createSimpleDeclarationList() {
    return Iterators.peekingIterator(
        Arrays.asList(
                new Token(TokenType.KEYWORD, "let"),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.ALLOCATOR, ":"),
                new Token(TokenType.TYPE, "number"),
                new Token(TokenType.END, ";"))
            .iterator());
  }

  public static PeekingIterator<Token> createSimpleNumberAssigmentList() {
    return Iterators.peekingIterator(
        Arrays.asList(
                new Token(TokenType.KEYWORD, "let"),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.ALLOCATOR, ":"),
                new Token(TokenType.TYPE, "number"),
                new Token(TokenType.EQUAL, "="),
                new Token(TokenType.NUMBER_VALUE, "3"),
                new Token(TokenType.END, ";"))
            .iterator());
  }

  public static PeekingIterator<Token> createPrintComplexStringList() {
    return Iterators.peekingIterator(
        Arrays.asList(
                new Token(TokenType.PRINT, "printLn"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.STRING_VALUE, "\"Hello World\""),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.END, ";"))
            .iterator());
  }

  public static PeekingIterator<Token> createComplexEquationList() {
    return Iterators.peekingIterator(
        Arrays.asList(
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
                new Token(TokenType.END, ";"))
            .iterator());
  }

  public static PeekingIterator<Token> createMultipleLinesList() {
    return Iterators.peekingIterator(
        Arrays.asList(
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
                new Token(TokenType.END, ";"))
            .iterator());
  }

  public static PeekingIterator<Token> createIfElseSentenceList() {
    return Iterators.peekingIterator(
        Arrays.asList(
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
                new Token(TokenType.RIGHT_BRACKET, "}"))
            .iterator());
  }

  public static PeekingIterator<Token> createFullVersionList() {
    return Iterators.peekingIterator(
        Iterators.concat(createMultipleLinesList(), createIfElseSentenceList()));
    //    return Stream.concat(createMultipleLinesList(), createIfElseSentenceList())
    //        .collect(Collectors.toList());
  }
}
