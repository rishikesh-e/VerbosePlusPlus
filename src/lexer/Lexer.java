package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String text;
    private int pos = 0;
    private char currentChar;
    private int line = 1;
    private int column = 1;

    public Lexer(String text) {
        this.text = text;
        currentChar = text.charAt(pos);
    }

    private void advance() {
        if (currentChar == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }

        pos++;
        if (pos < text.length()) {
            currentChar = text.charAt(pos);
        } else {
            currentChar = '\0';
        }
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                advance();
            } else if (Character.isDigit(currentChar)) {
                int startColumn = column;
                String num = number();
                TokenType type = num.contains(".") ? TokenType.FLOAT_LITERAL : TokenType.INT_LITERAL;
                tokens.add(new Token(type, num, line, startColumn));
            } else if (currentChar == '\'') {
                int startColumn = column;
                advance();
                char character = currentChar;
                advance();
                if (currentChar == '\'') {
                    advance();
                    tokens.add(new Token(TokenType.CHAR_LITERAL, String.valueOf(character), line, startColumn));
                } else {
                    throw new RuntimeException("Unterminated character literal at line " + line + ", column " + startColumn);
                }
            } else if (Character.isLetter(currentChar)) {
                int startColumn = column;
                String word = word();
                TokenType type = switch (word) {
                    case "variable" -> TokenType.VARIABLE;
                    case "if" -> TokenType.IF;
                    case "else" -> TokenType.ELSE;
                    case "terminal" -> TokenType.IDENTIFIER;
                    case "for" -> TokenType.FOR;
                    case "integer" -> TokenType.INT;
                    case "long" -> TokenType.LONG;
                    case "float" -> TokenType.FLOAT;
                    case "double" -> TokenType.DOUBLE;
                    case "character" -> TokenType.CHARACTER;
                    case "string" -> TokenType.STRING_TYPE;
                    case "boolean" -> TokenType.BOOLEAN;
                    case "true", "false" -> TokenType.BOOLEAN_LITERAL;
                    default -> TokenType.IDENTIFIER;
                };
                tokens.add(new Token(type, word, line, startColumn));
            } else {
                int startColumn = column;
                switch (currentChar) {
                    case '.' -> { tokens.add(new Token(TokenType.DOT, ".", line, startColumn)); advance(); }
                    case '(' -> { tokens.add(new Token(TokenType.LPAREN, "(", line, startColumn)); advance(); }
                    case ')' -> { tokens.add(new Token(TokenType.RPAREN, ")", line, startColumn)); advance(); }
                    case '{' -> { tokens.add(new Token(TokenType.LBRACE, "{", line, startColumn)); advance(); }
                    case '}' -> { tokens.add(new Token(TokenType.RBRACE, "}", line, startColumn)); advance(); }
                    case ';' -> { tokens.add(new Token(TokenType.SEMICOLON, ";", line, startColumn)); advance(); }
                    case ':' -> {
                        advance();
                        if (currentChar == '=') {
                            tokens.add(new Token(TokenType.ASSIGN, ":=", line, startColumn));
                            advance();
                        }
                    }
                    case '"' -> {
                        String str = string();
                        tokens.add(new Token(TokenType.STRING_LITERAL, str, line, startColumn));
                    }
                    case '+' -> { tokens.add(new Token(TokenType.PLUS, "+", line, startColumn)); advance(); }
                    case '-' -> { tokens.add(new Token(TokenType.MINUS, "-", line, startColumn)); advance(); }
                    case '*' -> { tokens.add(new Token(TokenType.MUL, "*", line, startColumn)); advance(); }
                    case '/' -> { tokens.add(new Token(TokenType.DIV, "/", line, startColumn)); advance(); }
                    case '>' -> {
                        advance();
                        if (currentChar == '=') { tokens.add(new Token(TokenType.GTE, ">=", line, startColumn)); advance(); }
                        else tokens.add(new Token(TokenType.GT, ">", line, startColumn));
                    }
                    case '<' -> {
                        advance();
                        if (currentChar == '=') { tokens.add(new Token(TokenType.LTE, "<=", line, startColumn)); advance(); }
                        else tokens.add(new Token(TokenType.LT, "<", line, startColumn));
                    }
                    case '=' -> {
                        advance();
                        if (currentChar == '=') { tokens.add(new Token(TokenType.EQ, "==", line, startColumn)); advance(); }
                    }
                    case '!' -> {
                        advance();
                        if (currentChar == '=') { tokens.add(new Token(TokenType.NEQ, "!=", line, startColumn)); advance(); }
                    }
                    default -> advance();
                }
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    private String number() {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            sb.append(currentChar);
            advance();
        }
        if (currentChar == '.') {
            sb.append(currentChar);
            advance();
            while (Character.isDigit(currentChar)) {
                sb.append(currentChar);
                advance();
            }
        }
        if ("fFdDlL".indexOf(currentChar) != -1) {
            sb.append(currentChar);
            advance();
        }
        return sb.toString();
    }

    private String string() {
        advance();
        StringBuilder sb = new StringBuilder();
        while (currentChar != '"' && currentChar != '\0') {
            sb.append(currentChar);
            advance();
        }
        advance();
        return sb.toString();
    }

    private String word() {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetter(currentChar)) {
            sb.append(currentChar);
            advance();
        }
        return sb.toString();
    }
}
