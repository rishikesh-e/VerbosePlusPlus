package parser;

public class CharacterLiteral implements AST {
    public char value;
    public CharacterLiteral(char value) {
        this.value = value;
    }
}