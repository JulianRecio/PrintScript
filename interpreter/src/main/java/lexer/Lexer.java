package lexer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private static Pattern KEYWORD_PATTERN;
    private static Pattern IDENTIFIER_PATTERN;
    private static Pattern ALLOCATOR_PATTER;
    private static Pattern TYPE_PATTERN;
    private static Pattern EQUAL_PATTERN;
    private static Pattern NUMBER_PATTERN;
    private static Pattern UNARY_PATTERN;
    private static Pattern STRING_PATTERN;
    private static Pattern OPERATOR_PATTERN;
    private static Pattern PRINT_PATTERN;
    private static Pattern LEFT_PARENTHESIS_PATTERN;
    private static Pattern RIGHT_PARENTHESIS_PATTERN;
    private static Pattern END;
    private static Pattern SPACE_PATTERN;
    private static Pattern IF;
    private static Pattern LEFT_BRACKET;
    private static Pattern RIGHT_BRACKET;
    private static Pattern ELSE;
    private static Pattern READ_INPUT;

    public static List<Token> tokenize(String input, Double version) {
        List<Token> tokens = new ArrayList<Token>();
        int pos = 0;
        setVersionPatterns(version);
        while (pos < input.length()) {
            String remainder = input.substring(pos);
            Matcher keywordMatcher = KEYWORD_PATTERN.matcher(remainder);
            Matcher identifierMatcher = IDENTIFIER_PATTERN.matcher(remainder);
            Matcher allcocatorMatcher = ALLOCATOR_PATTER.matcher(remainder);
            Matcher typeMatcher = TYPE_PATTERN.matcher(remainder);
            Matcher equalMatcher = EQUAL_PATTERN.matcher(remainder);
            Matcher numberMatcher = NUMBER_PATTERN.matcher(remainder);
            Matcher unartMatcher = UNARY_PATTERN.matcher(remainder);
            Matcher stringMatcher = STRING_PATTERN.matcher(remainder);
            Matcher operatorMatcher = OPERATOR_PATTERN.matcher(remainder);
            Matcher printMatcher = PRINT_PATTERN.matcher(remainder);
            Matcher leftParMatcher = LEFT_PARENTHESIS_PATTERN.matcher(remainder);
            Matcher rightParMatcher = RIGHT_PARENTHESIS_PATTERN.matcher(remainder);
            Matcher endMatcher = END.matcher(remainder);
            Matcher spaceMatcher = SPACE_PATTERN.matcher(remainder);
            if (isIdentifier(keywordMatcher, identifierMatcher, typeMatcher)) {
                String identifier = identifierMatcher.group();
                tokens.add(new Token(TokenType.IDENTIFIER, identifier));
                pos += identifier.length();
            }
            else if (printMatcher.lookingAt()){
                String print = printMatcher.group();
                tokens.add(new Token(TokenType.PRINT, print));
                pos += print.length();
            }
            else if (keywordMatcher.lookingAt()){
                String keyword = keywordMatcher.group();
                tokens.add(new Token(TokenType.KEYWORD, keyword));
                pos += keyword.length();
            }
            else if (allcocatorMatcher.lookingAt()){
                String allocator = allcocatorMatcher.group();
                tokens.add(new Token(TokenType.ALLOCATOR, allocator));
                pos += allocator.length();
            }
            else if (typeMatcher.lookingAt()){
                String type = typeMatcher.group();
                tokens.add(new Token(TokenType.TYPE, type));
                pos += type.length();
            }
            else if (equalMatcher.lookingAt()){
                String operator = equalMatcher.group();
                tokens.add(new Token(TokenType.EQUAL, operator));
                pos += operator.length();
            }
            else if (numberMatcher.lookingAt()){
                String number = numberMatcher.group();
                tokens.add(new Token(TokenType.NUMBER_VALUE, number));
                pos += number.length();
            }
            else if (unartMatcher.lookingAt()){
                String unary = unartMatcher.group();
                tokens.add(new Token(TokenType.UNARY_VALUE, unary));
                pos += unary.length();
            }
            else if (endMatcher.lookingAt()){
                String end = endMatcher.group();
                tokens.add(new Token(TokenType.END, end));
                pos += end.length();
            }
            else if (stringMatcher.lookingAt()){
                String string = stringMatcher.group();
                tokens.add(new Token(TokenType.STRING_VALUE, string));
                pos += string.length();
            }
            else if (operatorMatcher.lookingAt()){
                String operator = operatorMatcher.group();
                tokens.add(new Token(TokenType.OPERATOR, operator));
                pos += operator.length();
            }
            else if (leftParMatcher.lookingAt()){
                String leftPar = leftParMatcher.group();
                tokens.add(new Token(TokenType.LEFT_PARENTHESIS, leftPar));
                pos += leftPar.length();
            }
            else if (rightParMatcher.lookingAt()){
                String rightPar = rightParMatcher.group();
                tokens.add(new Token(TokenType.RIGHT_PARENTHESIS, rightPar));
                pos += rightPar.length();
            }
            else if (spaceMatcher.lookingAt()){
                String space = spaceMatcher.group();
                pos += space.length();
            }
            else if (version == 1.1){
                pos = addExtendedTokens(tokens, pos, remainder);
            }
            else {
                // If none of the patterns match, raise an error
                throw new RuntimeException("Invalid input at position " + pos);
            }
        }
        return tokens;
    }

    private static int addExtendedTokens(List<Token> tokens, int pos, String remainder) {
        Matcher ifMatcher = IF.matcher(remainder);
        Matcher leftBracketMatcher = LEFT_BRACKET.matcher(remainder);
        Matcher rightBracketMatcher = RIGHT_BRACKET.matcher(remainder);
        Matcher elseMatcher = ELSE.matcher(remainder);
        Matcher readInputMatcher = READ_INPUT.matcher(remainder);
        if (ifMatcher.lookingAt()){
            String ifState = ifMatcher.group();
            tokens.add(new Token(TokenType.IF, ifState));
            pos += ifState.length();
        }
        else if (leftBracketMatcher.lookingAt()){
            String leftBracket = leftBracketMatcher.group();
            tokens.add(new Token(TokenType.LEFT_BRACKET, leftBracket));
            pos += leftBracket.length();
        }
        else if (rightBracketMatcher.lookingAt()){
            String rightBracket = rightBracketMatcher.group();
            tokens.add(new Token(TokenType.RIGHT_BRACKET, rightBracket));
            pos += rightBracket.length();
        }
        else if (elseMatcher.lookingAt()){
            String elseState = elseMatcher.group();
            tokens.add(new Token(TokenType.ELSE, elseState));
            pos += elseState.length();
        }
        else if (readInputMatcher.lookingAt()){
            String readInput = readInputMatcher.group();
            tokens.add(new Token(TokenType.READ_INPUT, readInput));
            pos += readInput.length();
        }
        else {
            // If none of the patterns match, raise an error
            throw new RuntimeException("Invalid input at position " + pos);
        }
        return pos;
    }

    private static void setVersionPatterns(Double version) {
        IDENTIFIER_PATTERN = Pattern.compile("(?!PrintLn\\b)(?!readInput\\b)[a-zA-Z]+(_[a-zA-Z0-9]+)*");
        ALLOCATOR_PATTER = Pattern.compile(":");
        EQUAL_PATTERN = Pattern.compile("=");
        NUMBER_PATTERN = Pattern.compile("-?(0|[1-9]\\d*)(\\.\\d+)?");
        UNARY_PATTERN = Pattern.compile("-[a-zA-Z][a-zA-Z0-9]*");
        STRING_PATTERN = Pattern.compile("\"[a-zA-Z][a-zA-Z0-9 ]*\"");
        OPERATOR_PATTERN = Pattern.compile("[+\\-*/]");
        PRINT_PATTERN = Pattern.compile("PrintLn");
        LEFT_PARENTHESIS_PATTERN = Pattern.compile("\\(");
        RIGHT_PARENTHESIS_PATTERN = Pattern.compile("\\)");
        END = Pattern.compile(";");
        SPACE_PATTERN = Pattern.compile("\\s+");
        if (version == 1.0){
            KEYWORD_PATTERN = Pattern.compile("let");
            TYPE_PATTERN = Pattern.compile("number|string");
        }
        else if (version == 1.1){
            KEYWORD_PATTERN = Pattern.compile("let|const");
            TYPE_PATTERN = Pattern.compile("number|string|boolean");
            IF = Pattern.compile("if");
            LEFT_BRACKET = Pattern.compile("\\{");
            RIGHT_BRACKET = Pattern.compile("}");
            ELSE = Pattern.compile("else");
            READ_INPUT = Pattern.compile("readInput"); 
        }
        else {
            throw new RuntimeException("Invalid version");
        }
    }

    private static boolean isIdentifier(Matcher keywordMatcher, Matcher identifierMatcher, Matcher typeMatcher) {
        return identifierMatcher.lookingAt() && !keywordMatcher.lookingAt() && !typeMatcher.lookingAt();
    }
}
