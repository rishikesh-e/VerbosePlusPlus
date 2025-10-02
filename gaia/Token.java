package gaia;

public class Token {
    final TokenType type;
    public String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return type + ": " + value;
    }
}
