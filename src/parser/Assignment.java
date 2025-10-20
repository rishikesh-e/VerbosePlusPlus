package parser;

public class Assignment implements AST {
    public final String variableName;
    public final AST value;

    public Assignment(String variableName, AST value) {
        this.variableName = variableName;
        this.value = value;
    }
}