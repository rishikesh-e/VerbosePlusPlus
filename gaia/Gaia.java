package gaia;

import java.util.List;

public class Gaia {
    public static void main(String[] args) {
        //System.out.println("Safe commit, In case of rollback");
        String code = """
                variable(int) a := 10;
                variable(int) b := 20;
                variable(int) c := 15;
                if a > b {
                    if a > c {
                        terminal.print("a is greatest");
                    } else {
                        terminal.print("c is greatest");
                    }
                } else {
                    if b > c {
                        terminal.print("b is greatest");
                    } else {
                        terminal.print("c is greatest");
                    }
                }
                for(variable(int) i := 1; i < 5; i := i + 1) {
                    terminal.print(i/2);
                }
        """;

        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        List<AST> program = parser.parseCode();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(program);
    }
}
