import lexer.Lexer;
import lexer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import rule.Rule;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FormatterTest {

    private List<Rule> createRules(boolean spaceBeforeColonInDeclaration, boolean spaceAfterColonInDeclaration, boolean spaceBeforeAndAfterEqualSignInAssignment, int amountOfLineBreaksBeforePrintLn) {
        Map<String, String> ruleMap = new HashMap<>();
        //Inserting key-value pairs into map

        ruleMap.put("spaceBeforeColonInDeclaration", "" + spaceBeforeColonInDeclaration);
        ruleMap.put("spaceAfterColonInDeclaration", "" + spaceAfterColonInDeclaration);
        ruleMap.put("spaceBeforeAndAfterEqualSignInAssignment", "" + spaceBeforeAndAfterEqualSignInAssignment);
        ruleMap.put("AmountOfLineBreaksBeforePrintLn", "" + amountOfLineBreaksBeforePrintLn);

        return Formatter.getRulesFromConfig(ruleMap);
    }
    private List<Token> createTokensFromString(String input) {
        return Lexer.tokenize(input);
    }


//    @Test
//    public void testWholeFormatter()  {
//        InputStream inputStream = new ByteArrayInputStream("let x:number;".getBytes());
//        List<String> formattedCode = Formatter.useFormatter(inputStream);
//
//        Assertions.assertEquals(1, formattedCode.size());
//    }

    @Test
    public void testSpaceAfterColonInDeclarationRule()  {
        List<Rule> rules = createRules(false, true, false, 0);
        List<Token> tokens = createTokensFromString("let x:number;");

        List<String> expected = new ArrayList<>();
        expected.add("let x: number;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceBeforeColonInDeclarationRule()  {
        List<Rule> rules = createRules(true, false, false, 0);
        List<Token> tokens = createTokensFromString("let x:number;");

        List<String> expected = new ArrayList<>();
        expected.add("let x :number;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceBeforeAndAfterEqualSignInAssignmentRule()  {
        List<Rule> rules = createRules(false, false, true, 0);
        List<Token> tokens = createTokensFromString("let x:number=3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number = 3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs0()  {
        List<Rule> rules = createRules(false, false, false, 0);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");");

        List<String> expected = new ArrayList<>();
        expected.add("PrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }
    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs1()  {
        List<Rule> rules = createRules(false, false, false, 1);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");");

        List<String> expected = new ArrayList<>();
        expected.add("\nPrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }
    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs2()  {
        List<Rule> rules = createRules(false, false, false, 2);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");");

        List<String> expected = new ArrayList<>();
        expected.add("\n\nPrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testLineBreakAfterSemiColonRule()  {
        List<Rule> rules = createRules(false, false, false, 0);
        List<Token> tokens = createTokensFromString("let x:number=3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceBeforeAndAfterOperatorRule()  {
        List<Rule> rules = createRules(false, false, false, 0);
        List<Token> tokens = createTokensFromString("let x:number=3.6+3*3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3.6 + 3 * 3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testSpaceAfterKeywordRule()  {
        List<Rule> rules = createRules(false, false, false, 0);
        List<Token> tokens = createTokensFromString("letx:number=3;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testMaximumOf1SpaceBetweenTokensRule()  {
        List<Rule> rules = createRules(false, false, false, 0);
        List<Token> tokens = createTokensFromString("let    x    :    number    =     3   ;");

        List<String> expected = new ArrayList<>();
        expected.add("let x:number=3;");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        Assertions.assertEquals(expected.get(0), formattedCode.get(0));
    }

    @Test
    public void testMultipleLines()  {
        List<Rule> rules = createRules(true, true, true, 1);
        List<Token> tokens = createTokensFromString("let x:number=3.6;" +
                                                          "let x:string=\"Hola como\";" +
                                                          "PrintLn(\"Hola como\");" );

        List<String> expected = new ArrayList<>();
        expected.add("let x : number = 3.6;");
        expected.add("let x : string = \"Hola como\";");
        expected.add("\nPrintLn(\"Hola como\");");

        List<String> formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected.size(), formattedCode.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), formattedCode.get(i));
        }
    }
}
