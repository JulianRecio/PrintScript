package rule.rules;

import lexer.Token;
import rule.Rule;

import java.util.List;

public class MaximumOf1SpaceBetweenTokensRule implements Rule {


    @Override
    public void applyRule(List<Token> tokens) {
        Token prevToken = tokens.get(0);
        for (int i = 1; i < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            int spacesBetween = getSpaces(prevToken.getValue(), currentToken.getValue());
            if (spacesBetween >= 2){
                correctSpaces(prevToken, currentToken);
            }
            prevToken = currentToken;
        }
    }

    private void correctSpaces(Token prevToken, Token currentToken) {
        String prevValue = prevToken.getValue();
        String currValue = currentToken.getValue();

        int spacesPrev = amountOfSpacesEnd(prevValue.toCharArray());
        int spacesCurrent = amountOfSpacesBeginning(currValue.toCharArray());

        prevToken.setValue(prevValue.substring(0, prevValue.length()-spacesPrev));
        currentToken.setValue(currValue.substring(spacesCurrent-1)); // "    a"
    }

    private int getSpaces(String prevValue, String currentValue) {
        return amountOfSpacesEnd(prevValue.toCharArray()) + amountOfSpacesBeginning(currentValue.toCharArray());
    }
    private int amountOfSpacesEnd(char[] arr){
        int amountOfSpaces = 0;
        for (int i = arr.length-1; i >= 0; i--) {
            if (arr[i] == ' ') amountOfSpaces += 1;
            else break;
        }
        return amountOfSpaces;
    }
    private int amountOfSpacesBeginning(char[] arr){
        int amountOfSpaces = 0;
        for (char c : arr) {
            if (c == ' ') amountOfSpaces += 1;
            else break;
        }
        return amountOfSpaces;
    }
}
