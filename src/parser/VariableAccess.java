package parser;

public class VariableAccess implements AST {
    public String name;
    VariableAccess(String name) {
        this.name = name;
    }
}