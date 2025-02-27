package token;

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
