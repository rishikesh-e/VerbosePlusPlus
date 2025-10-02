package gaia;

import java.util.List;
import java.util.Scanner;

public class GaiaShell {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();

        System.out.println("Gaia Shell - type 'exit;' to quit");

        StringBuilder codeBuffer = new StringBuilder();

        while (true) {
            System.out.print(">>> ");
            String line = scanner.nextLine();

            if (line.trim().equals("exit;")) break;

            codeBuffer.append(line).append("\n");

            if (line.contains(";") || line.contains("{") || line.contains("}")) {
                try {
                    String code = codeBuffer.toString();
                    Lexer lexer = new Lexer(code);
                    List<Token> tokens = lexer.tokenize();

                    Parser parser = new Parser(tokens);
                    List<AST> ast = parser.parseCode();

                    interpreter.interpret(ast);

                    codeBuffer.setLength(0);
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        System.out.println("Exiting Gaia Shell.");
    }
}
