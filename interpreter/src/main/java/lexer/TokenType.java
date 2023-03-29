package lexer;

public enum TokenType {
    KEYWORD,            //let
    IDENTIFIER,         //variables
    ALLOCATOR,          //:
    TYPE,               //number o string
    OPERATOR,           //+ - * /
    EQUAL,              //=
    NUMBER_VALUE,
    STRING_VALUE,
    STRING_SYMBOL,
    PRINT,              //PrintLn()
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    END                 //;

}