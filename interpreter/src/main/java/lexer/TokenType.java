package lexer;

public enum TokenType {
  KEYWORD, // let
  IDENTIFIER, // variables
  ALLOCATOR, // :
  TYPE, // number o string
  OPERATOR, // + - * /
  EQUAL, // =
  NUMBER_VALUE,
  UNARY_VALUE,
  STRING_VALUE,
  BOOLEAN_VALUE,
  STRING_SYMBOL,
  PRINT, // PrintLn()
  LEFT_PARENTHESIS,
  RIGHT_PARENTHESIS,
  END, // ;
  IF,
  LEFT_BRACKET,
  RIGHT_BRACKET,
  ELSE,
  READ_INPUT // readInput();
}
