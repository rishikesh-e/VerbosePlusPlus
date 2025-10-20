package parser;

public class StringLiteral implements AST {
    public String value;
    public StringLiteral(String value) {
        this.value = value;
    }
}