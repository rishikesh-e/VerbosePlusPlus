package VerbosePlusPlus;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class VerbosePlusPlus {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: verbose++ <file.vpp>");
            return;
        }

        String code = Files.readString(Paths.get(args[0]));

        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        List<AST> program = parser.parseCode();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(program);
    }
}
