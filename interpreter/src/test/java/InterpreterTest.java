import ast.node.Node;
import com.google.common.collect.PeekingIterator;
import interpreter.Interpreter;
import java.io.*;
import java.util.Iterator;
import lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;
import token.Token;

public class InterpreterTest {

  //   Version 1.0 tests

  @Test
  public void testSimpleLine() {
    String toTokenize = "let x: number = 5; println(x);\n";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testVariableSave() {
    String toTokenize = "let x:number; x=5; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleVariableValueChangeWithSameVariable() {
    String toTokenize = "let x:number; x=5.0; let y:number = 8; x = x + y; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(13.0, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testExceptionWhenVariableAlreadyInitialized() {
    String toTokenize = "let x:number; x=5; let y:number = 8; let x:number = x + y; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
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
    String toTokenize = "let x:number; x=\"hello\";";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
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
    String toTokenize = "let x:number; x=5; let y:number; let z:number = x + y; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
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
    String toTokenize = "let x:number; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
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
    String toTokenize = "let x:number = (5+4)*2;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(18, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testMultipleOperations() {
    String toTokenize = "let cuenta: number = 5*5-8/4+2;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(25, interpreter.getMap().get("cuenta").getValue());
  }

  @Test
  public void printDivision() {
    String toTokenize = "let pi: number;\n" + "pi = 3.14;\n" + "println(pi / pi);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(3.14, interpreter.getMap().get("pi").getValue());
  }

  @Test
  public void printString() {
    String toTokenize = "let word: string = \"hello\"; println(word);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();

    // Redirect System.out to a ByteArrayOutputStream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream originalOut = System.out;
    System.setOut(printStream);

    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();

    // Restore the original System.out
    System.setOut(originalOut);

    // Convert the expected and actual output to strings
    String expectedOutput = "hello";
    String actualOutput = outputStream.toString().trim();

    // Assert that the actual output matches the expected output
    Assertions.assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void printNumberSumString() {
    String toTokenize =
        "let someNumber: number = 1;\n"
            + "let someString: string = \"hello world \";\n"
            + "println(someString + someNumber);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.0);
    Iterator<Node> nodes = parser.getNodeIterator();

    // Redirect System.out to a ByteArrayOutputStream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream originalOut = System.out;
    System.setOut(printStream);

    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();

    // Restore the original System.out
    System.setOut(originalOut);

    // Convert the expected and actual output to strings
    String expectedOutput = "hello world 1";
    String actualOutput = outputStream.toString().trim();

    // Assert that the actual output matches the expected output
    Assertions.assertEquals(expectedOutput, actualOutput);
  }

  // Version 1.1 tests

  @Test
  public void testSimpleLineVersion1() {
    String toTokenize = "let x:number = 5; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testConstDeclaration() {
    String toTokenize = "const x:number = 5; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(5, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testErrorWhenChangingConstValue() {
    String toTokenize = "const x:number = 5; x = 6;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
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
    String toTokenize = "let x:boolean = false; println(x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(false, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testSimpleIfElse() {
    String toTokenize = "if (false) {  } else {let x:number = 3;}";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
    Assertions.assertEquals(3, interpreter.getMap().get("x").getValue());
  }

  @Test
  public void testSimpleIf() {
    String toTokenize =
        "if (false) { println(\"if statement is not working correctly\");} println(\"outside of"
            + " conditional\");";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);
    interpreter.interpret();
  }

  @Test
  public void testReadInput() {
    String toTokenize =
        "let name:string = readInput(\"Name: \"); println(\"Hello \" + name + \"!\");";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    PeekingIterator<Token> tokens = lexer.getTokenIterator();
    Parser parser = new Parser(tokens, 1.1);
    Iterator<Node> nodes = parser.getNodeIterator();
    Interpreter interpreter = new Interpreter(nodes);

    String type = "world";
    InputStream in = new ByteArrayInputStream(type.getBytes());
    System.setIn(in);
    interpreter.interpret();
    Assertions.assertEquals("world", interpreter.getMap().get("name").getValue());
  }
}
