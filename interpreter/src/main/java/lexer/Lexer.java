package lexer;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class Lexer {

    private static final Pattern KEYWORD_PATTERN = Pattern.compile("let");
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");
    private static final Pattern ALLOCATOR_PATTER = Pattern.compile(":");
    private static final Pattern TYPE_PATTERN = Pattern.compile("number|string");
    private static final Pattern EQUAL_PATTERN = Pattern.compile("=");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?(0|[1-9]\\d*)(\\.\\d+)?");
    private static final Pattern STRING_PATTERN = Pattern.compile("\"[a-zA-Z][a-zA-Z0-9 ]*\"");
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("[+\\-*/]");
    private static final Pattern PRINT_PATTERN = Pattern.compile("PrintLn");
    private static final Pattern LEFT_PARENTHESIS_PATTERN = Pattern.compile("\\(");
    private static final Pattern RIGHT_PARENTHESIS_PATTERN = Pattern.compile("\\)");
    private static final Pattern END = Pattern.compile(";");
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");


    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<Token>();
        int pos = 0;
        while (pos < input.length()) {
            String remainder = input.substring(pos);
            Matcher keywordMatcher = KEYWORD_PATTERN.matcher(remainder);
            Matcher identifierMatcher = IDENTIFIER_PATTERN.matcher(remainder);
            Matcher allcocatorMatcher = ALLOCATOR_PATTER.matcher(remainder);
            Matcher typeMatcher = TYPE_PATTERN.matcher(remainder);
            Matcher equalMatcher = EQUAL_PATTERN.matcher(remainder);
            Matcher numberMatcher = NUMBER_PATTERN.matcher(remainder);
            Matcher stringMatcher = STRING_PATTERN.matcher(remainder);
            Matcher operatorMatcher = OPERATOR_PATTERN.matcher(remainder);
            Matcher printMatcher = PRINT_PATTERN.matcher(remainder);
            Matcher leftParMatcher = LEFT_PARENTHESIS_PATTERN.matcher(remainder);
            Matcher rightParMatcher = RIGHT_PARENTHESIS_PATTERN.matcher(remainder);
            Matcher endMatcher = END.matcher(remainder);
            Matcher spaceMatcher = SPACE_PATTERN.matcher(remainder);
            if (printMatcher.lookingAt()){
                String print = printMatcher.group();
                tokens.add(new Token(TokenType.PRINT, print));
                pos += print.length();
            }
            else if (isIdentifier(keywordMatcher, identifierMatcher, typeMatcher)) {
                String identifier = identifierMatcher.group();
                tokens.add(new Token(TokenType.IDENTIFIER, identifier));
                pos += identifier.length();
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
            else {
                // If none of the patterns match, raise an error
                throw new RuntimeException("Invalid input at position " + pos);
            }
        }
        return tokens;
    }

    private static boolean isIdentifier(Matcher keywordMatcher, Matcher identifierMatcher, Matcher typeMatcher) {
        return identifierMatcher.lookingAt() && !keywordMatcher.lookingAt() && !typeMatcher.lookingAt();
    }
}
