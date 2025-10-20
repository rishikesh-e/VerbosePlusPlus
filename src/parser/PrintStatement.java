package parser;

public class PrintStatement implements AST {
    public AST value;
    public PrintStatement(AST value) {
        this.value = value;
    }
}