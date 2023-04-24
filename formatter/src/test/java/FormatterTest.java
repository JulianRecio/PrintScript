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

    private List<Rule> createRules(Double version, boolean spaceBeforeColonInDeclaration, boolean spaceAfterColonInDeclaration, boolean spaceBeforeAndAfterEqualSignInAssignment, int amountOfLineBreaksBeforePrintLn, int amountOfIndentedInIfBlock, boolean IfKeyInSameLine) {
        Map<String, String> ruleMap = new HashMap<>();
        //Inserting key-value pairs into map

        if(version == 1.1){
            ruleMap.put("AmountOfIndentedInIfBlock", "" + amountOfIndentedInIfBlock);
            ruleMap.put("IfKeyInSameLine", "" + IfKeyInSameLine);
        }

        ruleMap.put("spaceBeforeColonInDeclaration", "" + spaceBeforeColonInDeclaration);
        ruleMap.put("spaceAfterColonInDeclaration", "" + spaceAfterColonInDeclaration);
        ruleMap.put("spaceBeforeAndAfterEqualSignInAssignment", "" + spaceBeforeAndAfterEqualSignInAssignment);
        ruleMap.put("AmountOfLineBreaksBeforePrintLn", "" + amountOfLineBreaksBeforePrintLn);

        return Formatter.getRulesFromConfig(ruleMap, version);
    }

    private List<Token> createTokensFromString(String input, Double version) {
        return Lexer.tokenize(input, version);
    }


    @Test
    public void testWholeFormatter()  {
//        InputStream inputStream = new ByteArrayInputStream("let x:number;".getBytes());
        String formattedCode = Formatter.useFormatter("let x:number;", 1.0);

        Assertions.assertNotEquals("", formattedCode);
    }

    @Test
    public void testSpaceAfterColonInDeclarationRule()  {
        List<Rule> rules = createRules(1.0,false, true, false, 0,0,false);
        List<Token> tokens = createTokensFromString("let x:number;", 1.0);

        String expected = "let x: number;\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testSpaceBeforeColonInDeclarationRule()  {
        List<Rule> rules = createRules(1.0,true, false, false, 0,0,false);
        List<Token> tokens = createTokensFromString("let x:number;", 1.0);

        String expected = "let x :number;\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testSpaceBeforeAndAfterEqualSignInAssignmentRule()  {
        List<Rule> rules = createRules(1.0,false, false, true, 0,0,false);
        List<Token> tokens = createTokensFromString("let x:number=3;", 1.0);

        String expected = "let x:number = 3;\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs0()  {
        List<Rule> rules = createRules(1.0,false, false, false, 0,0,false);
        List<Token> tokens = createTokensFromString("PrintLn(\"Hola como\");", 1.0);

        String expected = "PrintLn(\"Hola como\");\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }
    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs1()  {
        List<Rule> rules = createRules(1.0,false, false, false, 1,0,false);
        List<Token> tokens = createTokensFromString("printLn(\"Hola como\");", 1.0);

        String expected = "\nprintLn(\"Hola como\");\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }
    @Test
    public void testAmountOfLineBreaksBeforePrintLnRuleWhereAmountIs2()  {
        List<Rule> rules = createRules(1.0,false, false, false, 2,0,false);
        List<Token> tokens = createTokensFromString("printLn(\"Hola como\");", 1.0);

        String expected = "\n\nprintLn(\"Hola como\");\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testLineBreakAfterSemiColonRule()  {
        List<Rule> rules = createRules(1.0,false, false, false, 0,0,false);
        List<Token> tokens = createTokensFromString("let x:number=3;", 1.0);

        String expected = "let x:number=3;\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testSpaceBeforeAndAfterOperatorRule()  {
        List<Rule> rules = createRules(1.0,false, false, false, 0,0,false);
        List<Token> tokens = createTokensFromString("let x:number=3.6+3*3;", 1.0);

        String expected = "let x:number=3.6 + 3 * 3;\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testMaximumOf1SpaceBetweenTokensRule()  {
        List<Rule> rules = createRules(1.0,false, false, false, 0,0,false);
        List<Token> tokens = createTokensFromString("let    x    :    number    =     3   ;", 1.0);

        String expected = "let x:number=3;\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }

    @Test
    public void testMultipleLines()  {
        List<Rule> rules = createRules(1.0,true, true, true, 1,0,false);
        List<Token> tokens = createTokensFromString("let x:number=3.6;" +
                                                          "let x:string=\"Hola como\";" +
                                                          "printLn(\"Hola como\");" , 1.0);

        String expected = "let x : number = 3.6;\nlet x : string = \"Hola como\";\n\nprintLn(\"Hola como\");\n";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }


    //version 1.1

    @Test
    public void testAmountOfIndentedInIfBlockRuleWhereAmountIs0()  {
        List<Rule> rules = createRules(1.1,false, false, false, 0,0,false);
        List<Token> tokens = createTokensFromString("if (false) { let y:number = 7 } else {let x:number = 3;}", 1.1);

        String expected = "if(false){let y:number=7}else{let x:number=3;\n}";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }
    @Test
    public void testAmountOfIndentedInIfBlockRuleWhereAmountIs4()  {
        List<Rule> rules = createRules(1.1,false, false, false, 0,4,false);
        List<Token> tokens = createTokensFromString("if (false) { let y:number = 7; } else {let x:number = 3;}", 1.1);

        String expected = "if(false){    let y:number=7;\n}else{    let x:number=3;\n}";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }
    @Test
    public void testIfKeyOnSameLineRule()  {
        List<Rule> rules = createRules(1.1,false, false, false, 0,0,true);
        List<Token> tokens = createTokensFromString("if (false)\n { let y:number = 7; } else\n {let x:number = 3;}", 1.1);

        String expected = "if(false){\nlet y:number=7;\n}else{\nlet x:number=3;\n}";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }
    @Test
    public void testFullyVersion()  {
        List<Rule> rules = createRules(1.1,true, true, true, 2,3,true);
        List<Token> tokens = createTokensFromString("let x:number=3.6;" +
                "let x:     string=\"Hola como\";" +
                "printLn(x);" +
                "if(false)\n {let y:number=7;}else\n {let x:number=3;}"
                , 1.1);

        String expected = "let x : number = 3.6;\n" +
                "let x : string = \"Hola como\";\n" +
                "\n\nprintLn(x);\n" +
                "if(false){\n   let y : number = 7;\n}else{\n   let x : number = 3;\n}";

        String formattedCode = Formatter.format(tokens, rules);

        Assertions.assertEquals(expected, formattedCode);
    }
}
