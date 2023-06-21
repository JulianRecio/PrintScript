package parser;

import ast.node.*;
import com.google.common.collect.PeekingIterator;
import java.util.Iterator;
import parser.nodeParser.AssignationParser;
import parser.nodeParser.DeclarationParser;
import parser.nodeParser.IfCaseParser;
import parser.nodeParser.PrintParser;
import token.Token;
import token.TokenType;

public class Parser {

  private final Double version;
  private final PeekingIterator<Token> tokenIterator;
  private final Iterator<Node> nodeIterator;

  public Parser(PeekingIterator<Token> tokenIterator, Double version) {
    this.version = version;
    this.tokenIterator = tokenIterator;
    this.nodeIterator =
        new Iterator<>() {
          @Override
          public boolean hasNext() {
            return false;
          }

          @Override
          public Node next() {
            return parse();
          }
        };
  }

  public Node parse() {
    if (tokenIterator.hasNext()) {
      Token token = tokenIterator.peek();
      if (token.getType() == TokenType.KEYWORD) {
        return new DeclarationParser(tokenIterator, version).parseNode();
      } else if (token.getType() == TokenType.IDENTIFIER) {
        return new AssignationParser(tokenIterator).parseNode();
      } else if (token.getType() == TokenType.PRINT) {
        tokenIterator.remove();
        return new PrintParser(tokenIterator).parseNode();
      } else if (version == 1.1 && token.getType() == TokenType.IF) {
        tokenIterator.remove();
        return new IfCaseParser(tokenIterator, version).parseNode();
      } else {
        throw new RuntimeException("Line starts with incorrect token");
      }
    } else throw new RuntimeException("There are no more tokens");
  }

  public Iterator<Node> getNodeIterator() {
    return nodeIterator;
  }
}
