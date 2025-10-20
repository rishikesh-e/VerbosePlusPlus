package lexer;

public class Token {
    public final TokenType type;
    public String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return type + ": " + value;
    }
}
