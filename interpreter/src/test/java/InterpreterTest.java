import ast.AST;
import interpreter.Interpreter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
  public void testSimpleLine() throws IOException {
    String toTokenize = "let x:number = 5; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testVariableSave() throws IOException {
    String toTokenize = "let x:number; x=5; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleVariableValueChange() throws IOException {
    String toTokenize = "let x:number; x=5; let y:number = 8; x = 2 + y; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(10, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleVariableValueChangeWithSameVariable() throws IOException {
    String toTokenize = "let x:number; x=5.0; let y:number = 8; x = x + y; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(13.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testExceptionWhenVariableAlreadyInitialized() throws IOException {
    String toTokenize = "let x:number; x=5; let y:number = 8; let x:number = x + y; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
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
  public void testExceptionWhenMismatchingType() throws IOException {
    String toTokenize = "let x:number; x=\"hello\";";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
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
  public void testExceptionWhenVariableNotInitialized() throws IOException {
    String toTokenize = "let x:number; x=5; let y:number; let z:number = x + y; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
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
  public void testExceptionWhenVariableNotInitializedPrint() throws IOException {
    String toTokenize = "let x:number; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
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
  public void testExpressionWithParenthesis() throws IOException {
    String toTokenize = "let x:number = (5+4)*2;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(18, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleOperations() throws IOException {
    String toTokenize = "let cuenta: number = 5*5-8/4+2;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(25, interpreter.getMap().get("cuenta").getValue());
  }

  @Test
  public void printDivision() throws IOException {
    String toTokenize = "let pi: number;\n" + "pi = 3.14;\n" + "println(4 + 2);\n";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(3.14, interpreter.getMap().get("pi").getValue());
  }

  @Test
  public void printNumberSumString() throws IOException {
    String toTokenize =
        "let someNumber: number = 1;\n"
            + "let someString: string = \"hello world \";\n"
            + "println(someString + someNumber);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.0);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(3.14, interpreter.getMap().get("pi").getValue());
  }

  // Version 1.1 tests

  @Test
  public void testSimpleLineVersion1() throws IOException {
    String toTokenize = "let x:number = 5; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.1);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testConstDeclaration() throws IOException {
    String toTokenize = "const x:number = 5; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.1);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testErrorWhenChangingConstValue() throws IOException {
    String toTokenize = "const x:number = 5; x = 6;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.1);
    AST ast = parser.parse();
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
  public void testBooleanValue() throws IOException {
    String toTokenize = "let x:boolean = false; println(x);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.1);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(false, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testSimpleIf() throws IOException {
    String toTokenize = "if (false) {  } else {let x:number = 3;}";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.1);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    interpreter.interpret();
    Assertions.assertEquals(3, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testReadInput() throws IOException {
    String toTokenize = "let x:number = readInput(\"Insert variable\");";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens, 1.1);
    AST ast = parser.parse();
    Interpreter interpreter = new Interpreter(ast);
    String type = "number\n3";
    InputStream in = new ByteArrayInputStream(type.getBytes());
    System.setIn(in);
    interpreter.interpret();
    Assertions.assertEquals(3, interpreter.getMap().get("x").getValue());
  }
}
