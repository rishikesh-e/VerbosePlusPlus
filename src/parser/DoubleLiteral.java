package parser;

public class DoubleLiteral implements AST {
    public double value;
    public DoubleLiteral(double value) {
        this.value = value;
    }
}