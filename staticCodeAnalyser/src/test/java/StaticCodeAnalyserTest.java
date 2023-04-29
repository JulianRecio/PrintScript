import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import staticCodeAnalyser.StaticCodeAnalyser;

public class StaticCodeAnalyserTest {
  private String createTestConfigFileAndReturnPath(
      String testName,
      String identifierCase,
      boolean printLnCondition,
      boolean readInputCondition) {
    JSONObject jsonObject = new JSONObject();
    // Inserting key-value pairs into the json object
    jsonObject.put("caseConvention", "" + identifierCase);
    jsonObject.put("printLnCondition", "" + printLnCondition);
    jsonObject.put("readInputCondition", "" + readInputCondition);

    String path = "src\\test\\resources\\" + testName + "Config.json5";
    try {
      FileWriter fw = new FileWriter(path);
      fw.write(jsonObject.toJSONString());
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return path;
  }

  @Test
  public void testJsonConfigConditionsWhenIsSnakeCaseAndPrintIsTrue() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testJsonConfigConditionsWhenIsCamelCaseAndPrintIsTrue", "snake case", true, true),
            1.0);

    Assertions.assertEquals("snake case", sca.getCaseConvention());
    Assertions.assertEquals(true, sca.isPrintLnCondition());
  }

  @Test
  public void testJsonConfigConditionsWhenIsCamelCaseAndPrintIsFalse() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testJsonConfigConditionsWhenIsCamelCaseAndPrintIsTrue", "camel case", false, true),
            1.0);

    Assertions.assertEquals("camel case", sca.getCaseConvention());
    Assertions.assertEquals(false, sca.isPrintLnCondition());
  }

  @Test
  public void testAnalyzeWhenIsSnakeCaseShouldReturnWithoutError() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsSnakeCaseShouldReturnWithoutError", "snake case", true, true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createDeclarationAST("snake_case_variable"));

    Assertions.assertEquals(0, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsCamelCaseShouldReturnWithoutError() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsCamelCaseShouldReturnWithoutError", "camel case", true, true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createDeclarationAST("camelCaseVariable"));

    Assertions.assertEquals(0, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsSnakeCaseShouldReturnWith1Error() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsSnakeCaseShouldReturnWith1Error", "snake case", true, true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createDeclarationAST("snakeCaseVariable"));

    Assertions.assertEquals(1, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsCamelCaseShouldReturnWith1Error() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsCamelCaseShouldReturnWith1Error", "camel case", true, true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createDeclarationAST("camel_case_variable"));

    Assertions.assertEquals(1, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsSnakeCaseShouldReturnWith2Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsSnakeCaseShouldReturnWith2Errors", "snake case", true, true),
            1.0);

    List<String> expected =
        sca.analyze(
            ASTCases.createMultipleDeclarationAST("snakeCaseVariable", "snake_CaseVariable2"));

    Assertions.assertEquals(2, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsCamelCaseShouldReturnWith2Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsCamelCaseShouldReturnWith2Errors", "camel case", true, true),
            1.0);

    List<String> expected =
        sca.analyze(
            ASTCases.createMultipleDeclarationAST("camel_case_variable", "camel_case_variable2"));

    Assertions.assertEquals(2, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsPrintLnConditionTrueReturnWith1Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsPrintLnConditionTrueReturnWith1Errors", "camel case", true, true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createPrintAST());

    Assertions.assertEquals(1, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsPrintLnConditionFalseReturnWith0Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsPrintLnConditionFalseReturnWith0Errors",
                "camel case",
                false,
                true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createPrintAST());

    Assertions.assertEquals(0, expected.size());
  }

  @Test
  public void testAnalyzeWhenIsPrintLnConditionTrueReturnWith2Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsPrintLnConditionTrueReturnWith2Errors", "camel case", true, true),
            1.0);

    List<String> expected = sca.analyze(ASTCases.createMultiplePrintAST());

    Assertions.assertEquals(2, expected.size());
  }

  @Test
  public void testJsonConfigConditionsWhenReadInputIsTrue() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testJsonConfigConditionsWhenIsCamelCaseAndPrintIsTrue", "snake case", true, true),
            1.1);

    Assertions.assertEquals(true, sca.isReadInputCondition());
  }

  @Test
  public void testJsonConfigConditionsWhenReadInputIsFalse() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testJsonConfigConditionsWhenIsCamelCaseAndPrintIsTrue", "snake case", true, false),
            1.1);

    Assertions.assertEquals(false, sca.isReadInputCondition());
  }

  @Test
  public void testAnalyzeWhenIsReadInputConditionIsFalseReturnWith0Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsPrintLnConditionFalseReturnWith0Errors",
                "camel case",
                false,
                false),
            1.1);

    String code = "let x:string = readInput(\"Hola como\");";
    List<String> expected = sca.analyze(ASTCases.createReadInputAST());

    Assertions.assertEquals(0, expected.size());
  }

  @Test
  @Disabled
  public void testAnalyzeWhenIsReadInputConditionIsTrueReturnWith1Errors() throws IOException {
    StaticCodeAnalyser sca =
        new StaticCodeAnalyser(
            createTestConfigFileAndReturnPath(
                "testAnalyzeWhenIsPrintLnConditionFalseReturnWith0Errors",
                "camel case",
                false,
                true),
            1.1);

    String code = "let x: number = 3; let y:string = readInput(\"Hola como\" + \"x\");";
    List<String> expected = sca.analyze(ASTCases.createReadInputAST());

    Assertions.assertEquals(1, expected.size());
  }
}
