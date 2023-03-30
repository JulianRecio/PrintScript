import lexer.Lexer;
import lexer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import rule.Rule;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FormatterTest {

    private List<Rule> createTestConfigFileAndReturnRules(String testName, boolean spaceBeforeColonInDeclaration, boolean spaceAfterColonInDeclaration, boolean spaceBeforeAndAfterEqualSignInAssignment, int amountOfLineBreaksBeforePrintLn) {
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("spaceBeforeColonInDeclaration", "" + spaceBeforeColonInDeclaration);
        jsonObject.put("spaceAfterColonInDeclaration", "" + spaceAfterColonInDeclaration);
        jsonObject.put("spaceBeforeAndAfterEqualSignInAssignment", "" + spaceBeforeAndAfterEqualSignInAssignment);
        jsonObject.put("AmountOfLineBreaksBeforePrintLn", "" + amountOfLineBreaksBeforePrintLn);

        String path = "src\\test\\resources\\" + testName + "Config.json";
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(jsonObject.toJSONString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Formatter.getRulesFromConfig(path);
    }
    private List<Token> createTokensFromString(String input) {
        return Lexer.tokenize(input);
    }

    @Test
    public void testSpaceAfterColonInDeclarationRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testSpaceAfterColonInDeclarationRule", false, true, false, 0);
        List<Token> tokens = createTokensFromString("let x:number;");

        List<String> expected = new ArrayList<>();
        expected.add("let x: number;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceBeforeColonInDeclarationRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testSpaceBeforeColonInDeclarationRule", true, false, false, 0);
        List<Token> tokens = createTokensFromString("let x:number;");

        List<String> expected = new ArrayList<>();
        expected.add("let x :number;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceBeforeAndAfterEqualSignInAssignmentRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testSpaceBeforeAndAfterEqualSignInAssignmentRule", false, false, true, 0);
        List<Token> tokens = createTokensFromString("let x:number=3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number = 3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs0()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs0", false, false, false, 0);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");");

        List<String> expected = new ArrayList<>();
        expected.add("PrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }
    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs1()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs1", false, false, false, 1);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");");

        List<String> expected = new ArrayList<>();
        expected.add("\nPrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }
    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs2()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs2", false, false, false, 2);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");");

        List<String> expected = new ArrayList<>();
        expected.add("\n\nPrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testLineBreakAfterSemiColonRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testLineBreakAfterSemiColonRule", false, false, false, 0);
        List<Token> tokens = createTokensFromString("let x:number=3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceBeforeAndAfterOperatorRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testSpaceBeforeAndAfterOperatorRule", false, false, false, 0);
        List<Token> tokens = createTokensFromString("let x:number=3.6+3*3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3.6 + 3 * 3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceAfterKeywordRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testSpaceAfterKeywordRule", false, false, false, 0);
        List<Token> tokens = createTokensFromString("letx:number=3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testMaximumOf1SpaceBetweenTokensRule()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testMaximumOf1SpaceBetweenTokensRule", false, false, false, 0);
        List<Token> tokens = createTokensFromString("let    x    :    number    =     3   ;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testMultipleLines()  {
        List<Rule> rules = createTestConfigFileAndReturnRules("testMultipleLines", true, true, true, 1);
        List<Token> tokens = createTokensFromString("let x:number=3.6;" +
                                                          "let x:string=\"Hola como\";");

        List<String> expected = new ArrayList<>();
        expected.add("let x : number = 3.6;");
        expected.add("let x : string = \"Hola como\";");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), formattedCode.get(i));
        }
    }

}
