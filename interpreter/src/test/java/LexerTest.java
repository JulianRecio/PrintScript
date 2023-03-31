import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LexerTest {


    @Test
    public void testSimpleLineNumberDeclaration(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineStringDeclaration(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.STRING_TYPE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:string;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testMultipleSpaces(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let         x:number;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineStringDeclarationWithComplexIdentifier(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.COMPLEX_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.STRING_TYPE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let variable:string;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineNumberAssignment(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineDecimalNumberAssignment(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.DECIMAL_NUMBER_VALUE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3.6;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineStringAssignment(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.STRING_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.SIMPLE_STRING_VALUE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:string = \"a\";");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testSimpleLineComplexStringAssignments(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.STRING_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:string = \"Hola como\";");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testOperators(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.DECIMAL_NUMBER_VALUE);
        expResult.add(TokenExamples.PLUS);
        expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
        expResult.add(TokenExamples.MULTIPLICATION);
        expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x:number = 3.6 + 3 * 3;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testComplexLineStringAndNumberAssignments(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.DECIMAL_NUMBER_VALUE);
        expResult.add(TokenExamples.END);
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.STRING_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
        expResult.add(TokenExamples.END);
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
        expResult.add(TokenExamples.PRINT);
        expResult.add(TokenExamples.LEFT_PAR);
        expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
        expResult.add(TokenExamples.PLUS);
        expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
        expResult.add(TokenExamples.RIGHT_PAR);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("PrintLn(\"Hola como\" + 3);");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testPrintMethodExpression(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.KEYWORD);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.ALLOCATOR);
        expResult.add(TokenExamples.NUMBER_TYPE);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
        expResult.add(TokenExamples.END);
        expResult.add(TokenExamples.PRINT);
        expResult.add(TokenExamples.LEFT_PAR);
        expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
        expResult.add(TokenExamples.PLUS);
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.RIGHT_PAR);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("let x: number = 3; PrintLn(\"Hola como\" + x);");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }

    @Test
    public void testUnaryToken(){
        List<Token> expResult = new ArrayList<>();
        expResult.add(TokenExamples.SIMPLE_IDENTIFIER);
        expResult.add(TokenExamples.EQUAL);
        expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
        expResult.add(TokenExamples.PLUS);
        expResult.add(TokenExamples.UNARY_VALUE);
        expResult.add(TokenExamples.END);
        ArrayList<Token> list = (ArrayList<Token>) Lexer.tokenize("x = 3 + -variableA;");
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
            Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
        }
    }


}