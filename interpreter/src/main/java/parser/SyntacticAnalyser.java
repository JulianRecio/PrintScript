package parser;

import lexer.Token;
import lexer.TokenType;
import parser.ast.AST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SyntacticAnalyser {
    public List<List<TokenType>> getPatterns(){
        List<List<TokenType>> result = new ArrayList<>();
        result.add(Arrays.asList(TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.ALLOCATOR, TokenType.TYPE, TokenType.EQUAL, TokenType.STRING_SYMBOL,TokenType.STRING_VALUE, TokenType.STRING_SYMBOL));//let lastName: string = "Doe"
        result.add(Arrays.asList(TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.ALLOCATOR, TokenType.TYPE, TokenType.EQUAL, TokenType.NUMBER_VALUE));//let a: number = 12;
        result.add(Arrays.asList(TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.ALLOCATOR, TokenType.TYPE, TokenType.EQUAL, TokenType.IDENTIFIER));//let a: number = n;
        result.add(Arrays.asList(TokenType.IDENTIFIER, TokenType.ALLOCATOR, TokenType.IDENTIFIER));//a = a / b;
        result.add(Arrays.asList(TokenType.PRINT, TokenType.LEFT_PARENTHESIS));//println("Result: " + c)
        return result;
    }
    public AST analyse(List<Token> tokens){
        List<List<TokenType>> patterns = getPatterns();
        AST root = new AST();
        return root;
    }
}
