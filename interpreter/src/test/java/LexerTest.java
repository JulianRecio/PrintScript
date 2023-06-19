import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenExamples;

public class LexerTest {

  // Version 1.0 tests

  @Test
  public void testSimpleLineNumberDeclaration() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineStringDeclaration() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:string;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testMultipleSpaces() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let         x:number;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineStringDeclarationWithComplexIdentifier() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.COMPLEX_IDENTIFIER);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let variable:string;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineNumberAssignment() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineDecimalNumberAssignment() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3.6;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineStringAssignment() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_STRING_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:string = \"a\";";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineComplexStringAssignments() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:string = \"Hello World\";";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testOperators() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_NUMBER_VALUE);
    expResult.add(TokenExamples.PLUS);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.MULTIPLICATION);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3.6 + 3 * 3;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testComplexLineStringAndNumberAssignments() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3.6;" + "let x:string = \"Hello World\";";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testPrintMethod() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.PRINT);
    expResult.add(TokenExamples.LEFT_PAR);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.PLUS);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.RIGHT_PAR);
    expResult.add(TokenExamples.END);
    String toTokenize = "println(\"Hello World\" + 3);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testPrintMethodExpression() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    expResult.add(TokenExamples.PRINT);
    expResult.add(TokenExamples.LEFT_PAR);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.PLUS);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.RIGHT_PAR);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x: number = 3; println(\"Hello World\" + x);";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.0);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  // Version 1.1 tests

  @Test
  public void testSimpleLineNumberAssignmentVersion1() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleLineNumberAssignmentWithConst() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.CONST_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "const x:number = 3;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testBooleanValue() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.BOOLEAN_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.BOOLEAN_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:boolean = true;";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testSimpleIf() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.IF);
    expResult.add(TokenExamples.LEFT_PAR);
    expResult.add(TokenExamples.BOOLEAN_VALUE);
    expResult.add(TokenExamples.RIGHT_PAR);
    expResult.add(TokenExamples.LEFT_BRACKET);
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    expResult.add(TokenExamples.RIGHT_BRACKET);
    String toTokenize = "if (true) {let x:number = 3;}";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }

  @Test
  public void testReadInput() {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.READ_INPUT);
    expResult.add(TokenExamples.LEFT_PAR);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.RIGHT_PAR);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = readInput(\"Hello World\");";
    PushbackInputStream inputStream =
        new PushbackInputStream(new ByteArrayInputStream(toTokenize.getBytes()));
    Lexer lexer = new Lexer(inputStream, 1.1);
    Iterator<Token> tokens = lexer.getTokenIterator();
    for (Token value : expResult) {
      Token token = tokens.next();
      Assertions.assertEquals(value.getType(), token.getType());
      Assertions.assertEquals(value.getValue(), token.getValue());
    }
  }
}
