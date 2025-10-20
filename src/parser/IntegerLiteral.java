package parser;

public class IntegerLiteral implements AST {
    public int value;
    public IntegerLiteral(int value) {
        this.value = value;
    }
}