package gaia;

import java.util.List;

public class Gaia {
    public static void main(String[] args) {
        //System.out.println("Safe commit, In case of rollback");
        String code = """
            variable(int) x := 5;
            variable(int) y := 10;
            if x < y {
                terminal.print("y is greater");
            } else {
                terminal.print("x is greater");
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
