package parser;

public class Num implements AST {
    public double value;
    public Num(double value) {
        this.value = value;
    }
}