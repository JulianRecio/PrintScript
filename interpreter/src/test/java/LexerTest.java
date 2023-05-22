import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenExamples;

public class LexerTest {

  // Version 1.0 tests

  @Test
  public void testSimpleLineNumberDeclaration() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineStringDeclaration() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:string;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testMultipleSpaces() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let         x:number;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineStringDeclarationWithComplexIdentifier() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.COMPLEX_IDENTIFIER);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let variable:string;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineNumberAssignment() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineDecimalNumberAssignment() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3.6;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineStringAssignment() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_STRING_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:string = \"a\";";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineComplexStringAssignments() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.STRING_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:string = \"Hello World\";";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testOperators() throws IOException {
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
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testComplexLineStringAndNumberAssignments() throws IOException {
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
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testPrintMethod() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.PRINT);
    expResult.add(TokenExamples.LEFT_PAR);
    expResult.add(TokenExamples.COMPLEX_STRING_VALUE);
    expResult.add(TokenExamples.PLUS);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.RIGHT_PAR);
    expResult.add(TokenExamples.END);
    String toTokenize = "println(\"Hello World\" + 3);";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testPrintMethodExpression() throws IOException {
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
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.0);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  // Version 1.1 tests

  @Test
  public void testSimpleLineNumberAssignmentVersion1() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:number = 3;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleLineNumberAssignmentWithConst() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.CONST_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.NUMBER_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.SIMPLE_NUMBER_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "const x:number = 3;";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testBooleanValue() throws IOException {
    List<Token> expResult = new ArrayList<>();
    expResult.add(TokenExamples.LET_KEYWORD);
    expResult.add(TokenExamples.SIMPLE_IDENTIFIER_X);
    expResult.add(TokenExamples.ALLOCATOR);
    expResult.add(TokenExamples.BOOLEAN_TYPE);
    expResult.add(TokenExamples.EQUAL);
    expResult.add(TokenExamples.BOOLEAN_VALUE);
    expResult.add(TokenExamples.END);
    String toTokenize = "let x:boolean = true";
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testSimpleIf() throws IOException {
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
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }

  @Test
  public void testReadInput() throws IOException {
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
    InputStream inputStream = new ByteArrayInputStream(toTokenize.getBytes());
    Lexer lexer = new Lexer(inputStream, 1.1);
    List<Token> list = lexer.tokenize();
    for (int i = 0; i < list.size(); i++) {
      Assertions.assertEquals(expResult.get(i).getType(), list.get(i).getType());
      Assertions.assertEquals(expResult.get(i).getValue(), list.get(i).getValue());
    }
  }
}
