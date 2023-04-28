import ast.AST;
import interpreter.Interpreter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;
import token.Token;

public class InterpreterTest {

  // Version 1.0 tests

  @Test
  public void testSimpleLine() {
    List<Token> tokens = Lexer.tokenize("let x:number = 5; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testVariableSave() {
    List<Token> tokens = Lexer.tokenize("let x:number; x=5; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleVariableValueChange() {
    List<Token> tokens =
        Lexer.tokenize("let x:number; x=5.0; let y:number = 8.0; x = 2.0 + y; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(10.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleVariableValueChangeWithSameVariable() {
    List<Token> tokens =
        Lexer.tokenize("let x:number; x=5.0; let y:number = 8.0; x = x + y; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(13.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testExceptionWhenVariableAlreadyInitialized() {
    List<Token> tokens =
        Lexer.tokenize(
            "let x:number; x=5.0; let y:number = 8.0; let x:number = x + y; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    RuntimeException error = null;
    try {
      interpreter.interpret();
    } catch (RuntimeException e) {
      error = e;
    }
    assert error != null;
    Assertions.assertEquals("Variable x is already declared", error.getMessage());
  }

  @Test
  public void testExceptionWhenMismatchingType() {
    List<Token> tokens = Lexer.tokenize("let x:number; x=\"hello\";", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    RuntimeException error = null;
    try {
      interpreter.interpret();
    } catch (RuntimeException e) {
      error = e;
    }
    assert error != null;
    Assertions.assertEquals("Mismatching types", error.getMessage());
  }

  @Test
  public void testExceptionWhenVariableNotInitialized() {
    List<Token> tokens =
        Lexer.tokenize("let x:number; x=5.0; let y:number; let z:number = x + y; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    RuntimeException error = null;
    try {
      interpreter.interpret();
    } catch (RuntimeException e) {
      error = e;
    }
    assert error != null;
    Assertions.assertEquals("Variable y was not initialized", error.getMessage());
  }

  @Test
  public void testExceptionWhenVariableNotInitializedPrint() {
    List<Token> tokens = Lexer.tokenize("let x:number; printLn(x);", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    RuntimeException error = null;
    try {
      interpreter.interpret();
    } catch (RuntimeException e) {
      error = e;
    }
    assert error != null;
    Assertions.assertEquals("Variable x was not initialized", error.getMessage());
  }

  @Test
  public void testExpressionWithParenthesis() {
    List<Token> tokens = Lexer.tokenize("let x:number = (5+4)*2;", 1.0);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.0);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(18.0, interpreter.getMap().get("x").getValue());
  }

  // Version 1.1 tests

  @Test
  public void testSimpleLineVersion1() {
    List<Token> tokens = Lexer.tokenize("let x:number = 5; printLn(x);", 1.1);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.1);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testConstDeclaration() {
    List<Token> tokens = Lexer.tokenize("const x:number = 5; printLn(x);", 1.1);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.1);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testErrorWhenChangingConstValue() {
    List<Token> tokens = Lexer.tokenize("const x:number = 5; x = 6;", 1.1);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.1);
    Interpreter interpreter = new Interpreter(ast);
    RuntimeException error = null;
    try {
      interpreter.interpret();
    } catch (RuntimeException e) {
      error = e;
    }
    assert error != null;
    Assertions.assertEquals("Variable is const, cannot change value", error.getMessage());
  }

  @Test
  public void testBooleanValue() {
    List<Token> tokens = Lexer.tokenize("let x:boolean = false; printLn(x);", 1.1);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.1);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(false, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testSimpleIf() {
    List<Token> tokens = Lexer.tokenize("if (false) {  } else {let x:number = 3;}", 1.1);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.1);
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(3.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testReadInput() {
    List<Token> tokens = Lexer.tokenize("let x:number = readInput(\"Insert variable\");", 1.1);
    Parser parser = new Parser(tokens);
    AST ast = parser.parse(1.1);
    Interpreter interpreter = new Interpreter(ast);
    String type = "number\n3.0";
    InputStream in = new ByteArrayInputStream(type.getBytes());
    System.setIn(in);
    interpreter.interpret();
    Assertions.assertEquals(3.0, interpreter.getMap().get("x").getValue());
  }
}
