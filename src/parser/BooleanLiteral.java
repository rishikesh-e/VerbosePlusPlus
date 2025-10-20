package parser;

public class BooleanLiteral implements AST {
    public boolean value;
    public BooleanLiteral(boolean value) {
        this.value = value;
    }
}