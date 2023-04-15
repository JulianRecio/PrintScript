package edu.austral.ingsis.printscript.app;

import interpreter.Interpreter;
import lexer.Lexer;
import lexer.Token;
import parser.AST;
import parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        printMenu();
        int choice = scan.nextInt();
        String code = getString();
        while (choice != 4){
            switch (choice) {
                case 1 -> {
                    List<Token> tokens = Lexer.tokenize(code);
                    Parser parser = new Parser(tokens);
                    AST ast = parser.parse();
                    Interpreter interpreter = new Interpreter(ast);
                    interpreter.interpret();
                }
                case 2 -> {
                    System.out.println("Hola");
                }
                case 3 -> {
                    System.out.println("A");
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
            printMenu();
            choice = scan.nextInt();
        }

    }

    private static void printMenu() {
        System.out.println("1. Interpret");
        System.out.println("2. Format");
        System.out.println("3. Analyze");
        System.out.println("4. Exit");
    }

    public static String getString() throws IOException {
        Path filePath = Paths.get("./ToRun.txt");
        return Files.readString(filePath);
    }
}
