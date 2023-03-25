import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LexerTest {

    Token keyWord = new Token(TokenType.KEYWORD, "let");
    Token simpleIdentifier = new Token(TokenType.IDENTIFIER, "x");
    Token complexIdentifier = new Token(TokenType.IDENTIFIER, "variable");
    Token allocator = new Token(TokenType.ALLOCATOR, ":");
    Token numberType = new Token(TokenType.TYPE, "number");
    Token stringType = new Token(TokenType.TYPE, "string");
    Token equal = new Token(TokenType.EQUAL, "=");
    Token simpleNumberValue = new Token(TokenType.NUMBER_VALUE, "3");
    Token decimalNumberValue = new Token(TokenType.NUMBER_VALUE, "3.6");
    Token simpleStringValue = new Token(TokenType.STRING_VALUE, "\"a\"");
    Token complexStringValue = new Token(TokenType.STRING_VALUE, "\"Hola como\"");
    Token plus = new Token(TokenType.OPERATOR, "+");
    Token multiplication = new Token(TokenType.OPERATOR, "*");
    Token print = new Token(TokenType.PRINT, "PrintLn");
    Token leftPar = new Token(TokenType.LEFT_PARENTHESIS, "(");
    Token rightPar = new Token(TokenType.RIGHT_PARENTHESIS, ")");
    Token end = new Token(TokenType.END, ";");


    @Test
    public void testSimpleLineNumberDeclaration(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(numberType);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineStringDeclaration(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(stringType);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:string;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testMultipleSpaces(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(numberType);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let         x:number;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineStringDeclarationWithComplexIdentifier(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(complexIdentifier);
        expResult.add(allocator);
        expResult.add(stringType);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let variable:string;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineNumberAssignment(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(numberType);
        expResult.add(equal);
        expResult.add(simpleNumberValue);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineDecimalNumberAssignment(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(numberType);
        expResult.add(equal);
        expResult.add(decimalNumberValue);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3.6;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineStringAssignment(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(stringType);
        expResult.add(equal);
        expResult.add(simpleStringValue);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:string = \"a\";");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineComplexStringAssignments(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(stringType);
        expResult.add(equal);
        expResult.add(complexStringValue);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:string = \"Hola como\";");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testOperators(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(numberType);
        expResult.add(equal);
        expResult.add(decimalNumberValue);
        expResult.add(plus);
        expResult.add(simpleNumberValue);
        expResult.add(multiplication);
        expResult.add(simpleNumberValue);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3.6 + 3 * 3;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testComplexLineStringAndNumberAssignments(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(numberType);
        expResult.add(equal);
        expResult.add(decimalNumberValue);
        expResult.add(end);
        expResult.add(keyWord);
        expResult.add(simpleIdentifier);
        expResult.add(allocator);
        expResult.add(stringType);
        expResult.add(equal);
        expResult.add(complexStringValue);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3.6;" +
                                                                       "let x:string = \"Hola como\";");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testPrintMethod(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(print);
        expResult.add(leftPar);
        expResult.add(complexStringValue);
        expResult.add(plus);
        expResult.add(simpleNumberValue);
        expResult.add(rightPar);
        expResult.add(end);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("PrintLn(\"Hola como\" + 3);");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }


}