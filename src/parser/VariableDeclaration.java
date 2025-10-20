package parser;

public class VariableDeclaration implements AST{
    public String type;
    public String name;
    public AST value;
    public VariableDeclaration(String type, String name, AST value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}