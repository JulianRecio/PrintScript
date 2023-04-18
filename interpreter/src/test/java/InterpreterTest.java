import interpreter.Interpreter;
import lexer.Lexer;
import lexer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.AST;
import parser.Parser;

import java.util.List;

public class InterpreterTest {


    @Test
    public void testSimpleLine(){
        List<Token> tokens = Lexer.tokenize("let x:number = 5; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        interpreter.interpret();
        Assertions.assertEquals(5.0, interpreter.getMap().get("x").getValue());
    }

    @Test
    public void testVariableSave(){
        List<Token> tokens = Lexer.tokenize("let x:number; x=5; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        interpreter.interpret();
        Assertions.assertEquals(5.0, interpreter.getMap().get("x").getValue());
    }

    @Test
    public void testMultipleVariableValueChange(){
        List<Token> tokens = Lexer.tokenize("let x:number; x=5.0; let y:number = 8.0; x = 2.0 + y; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        interpreter.interpret();
        Assertions.assertEquals(10.0, interpreter.getMap().get("x").getValue());
    }

    @Test
    public void testMultipleVariableValueChangeWithSameVariable(){
        List<Token> tokens = Lexer.tokenize("let x:number; x=5.0; let y:number = 8.0; x = x + y; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        interpreter.interpret();
        Assertions.assertEquals(13.0, interpreter.getMap().get("x").getValue());
    }

    @Test
    public void testExceptionWhenVariableAlreadyInitialized(){
        List<Token> tokens = Lexer.tokenize("let x:number; x=5.0; let y:number = 8.0; let x:number = x + y; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        RuntimeException error = null;
        try {
            interpreter.interpret();
        }
        catch (RuntimeException e){
            error = e;
        }
        assert error != null;
        Assertions.assertEquals("Variable x is already declared", error.getMessage());
    }

    @Test
    public void testExceptionWhenMismatchingType(){
        List<Token> tokens = Lexer.tokenize("let x:number; x=\"hello\";", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        RuntimeException error = null;
        try {
            interpreter.interpret();
        }
        catch (RuntimeException e){
            error = e;
        }
        assert error != null;
        Assertions.assertEquals("Mismatching types", error.getMessage());
    }

    @Test
    public void testExceptionWhenVariableNotInitialized(){
        List<Token> tokens = Lexer.tokenize("let x:number; x=5.0; let y:number; let z:number = x + y; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        RuntimeException error = null;
        try {
            interpreter.interpret();
        }
        catch (RuntimeException e){
            error = e;
        }
        assert error != null;
        Assertions.assertEquals("Variable y was not initialized", error.getMessage());
    }

    @Test
    public void testExceptionWhenVariableNotInitializedPrint(){
        List<Token> tokens = Lexer.tokenize("let x:number; PrintLn(x);", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        RuntimeException error = null;
        try {
            interpreter.interpret();
        }
        catch (RuntimeException e){
            error = e;
        }
        assert error != null;
        Assertions.assertEquals("Variable x was not initialized", error.getMessage());
    }

    @Test
    public void testExpressionWithParenthesis(){
        List<Token> tokens = Lexer.tokenize("let x:number = (5+4)*2;", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        interpreter.interpret();
        Assertions.assertEquals(18.0, interpreter.getMap().get("x").getValue());
    }

    @Test
    public void testBooleanValue(){
        List<Token> tokens = Lexer.tokenize("let x:boolean = true", 1.0);
        Parser parser = new Parser(tokens);
        AST ast = parser.parse();
        Interpreter interpreter = new Interpreter(ast);
        interpreter.interpret();
        Assertions.assertEquals(true, interpreter.getMap().get("x").getValue());
    }
}
