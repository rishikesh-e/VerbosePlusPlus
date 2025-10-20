package parser;

public class LongLiteral implements AST {
    public long value;
    public LongLiteral(long value) {
        this.value = value;
    }
}