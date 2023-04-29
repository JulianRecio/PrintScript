package edu.austral.ingsis.printscript.app;

import formatter.Formatter;
import interpreter.Interpreter;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import lexer.Lexer;
import parser.Parser;
import staticCodeAnalyser.StaticCodeAnalyser;
import token.Token;

public class App {

  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);
    printInitialMenu();
    int choice = scan.nextInt();
    InputStream code = getString();
    Double version = null;
    while (choice != 3) {
      switch (choice) {
        case 1 -> {
          version = 1.0;
          runProgram(code, version);
        }
        case 2 -> {
          version = 1.1;
          runProgram(code, version);
        }
        default -> System.out.println("Invalid input");
      }
      printInitialMenu();
      choice = scan.nextInt();
    }
  }

  private static void runProgram(InputStream code, Double version) throws IOException {
    Scanner scan = new Scanner(System.in);
    printRunMenu();
    int choice = scan.nextInt();
    while (choice != 4) {
      switch (choice) {
        case 1 -> {
          List<Token> tokenList = Lexer.tokenize(code, version);
          Parser parser = new Parser(tokenList, version);
          Interpreter interpreter = new Interpreter(parser.parse());
          interpreter.interpret();
        }
        case 2 -> {
          List<Token> tokenList = Lexer.tokenize(code, version);
          String formatted =
              Formatter.format(
                  tokenList,
                  Formatter.getRulesFromConfig(
                      Formatter.readConfigFile("app\\src\\main\\resources\\formatterRules.json"),
                      version));
          BufferedWriter writer = new BufferedWriter(new FileWriter("./Result.txt"));
          writer.write(formatted);
          writer.close();
        }
        case 3 -> {
          List<Token> tokenList = Lexer.tokenize(code, version);
          StaticCodeAnalyser sca =
              new StaticCodeAnalyser(
                  "app\\src\\main\\resources\\staticCodeAnalyserRules.json", version);
          Parser parser = new Parser(tokenList, version);
          List<String> messages = sca.analyze(parser.parse());
          BufferedWriter writer = new BufferedWriter(new FileWriter("./Result.txt"));
          if (messages.size() == 0) {
            writer.write("There are no errors");
          } else {
            for (String message : messages) {
              writer.write(message);
            }
          }
          writer.close();
        }
        default -> System.out.println("Invalid input");
      }
      printRunMenu();
      choice = scan.nextInt();
    }
  }

  private static void printInitialMenu() {
    System.out.println("Select version");
    System.out.println("1. 1.0");
    System.out.println("2. 1.1");
    System.out.println("3. Quit");
  }

  private static void printRunMenu() {
    System.out.println("1. Interpret");
    System.out.println("2. Format");
    System.out.println("3. Analyze");
    System.out.println("4. Exit");
  }

  public static InputStream getString() throws IOException {
    Path filePath = Paths.get("./ToRun.txt");
    return new BufferedInputStream(new FileInputStream(new File(filePath.toUri())));
  }
}
