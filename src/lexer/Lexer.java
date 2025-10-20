package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String text;
    private int pos = 0;
    private char currentChar;
    private int line = 1;
    private int column = 1;

    public Lexer(String text) {
        this.text = text;
        currentChar = text.charAt(pos);
    }

    private void advance() {
        pos++;
        column++;
        if (pos < text.length()) {
            currentChar = text.charAt(pos);
        } else {
            currentChar = '\0';
        }
    }

    private void newLine() {
        line++;
        column = 1;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') {
                    newLine();
                }
                advance();
            }
            else if (Character.isDigit(currentChar)) {
                String number = number();
                if (number.contains(".")) {
                    tokens.add(new Token(TokenType.FLOAT_LITERAL, number, line, column));
                } else {
                    tokens.add(new Token(TokenType.INT_LITERAL, number, line, column));
                }
            }
            else if (currentChar == '\'') {
                advance();
                char character = currentChar;
                advance();
                if (currentChar == '\'') {
                    advance();
                    tokens.add(new Token(TokenType.CHAR_LITERAL, String.valueOf(character), line, column));
                } else {
                    throw new RuntimeException("Unterminated character literal at line " + line + ", column " + column);
                }
            }
            else if (Character.isLetter(currentChar)) {
                String word = word();
                switch (word) {
                    case "variable" -> tokens.add(new Token(TokenType.VARIABLE, word, line, column));
                    case "if" -> tokens.add(new Token(TokenType.IF, word, line, column));
                    case "else" -> tokens.add(new Token(TokenType.ELSE, word, line, column));
                    case "terminal" -> tokens.add(new Token(TokenType.IDENTIFIER, word, line, column));
                    case "for" -> tokens.add(new Token(TokenType.FOR, word, line, column));
                    case "integer" -> tokens.add(new Token(TokenType.INT, word, line, column));
                    case "long" -> tokens.add(new Token(TokenType.LONG, word, line, column));
                    case "float" -> tokens.add(new Token(TokenType.FLOAT, word, line, column));
                    case "double" -> tokens.add(new Token(TokenType.DOUBLE, word, line, column));
                    case "character" -> tokens.add(new Token(TokenType.CHARACTER, word, line, column));
                    case "string" -> tokens.add(new Token(TokenType.STRING_TYPE, word, line, column));
                    case "boolean" -> tokens.add(new Token(TokenType.BOOLEAN, word, line, column));
                    case "true", "false" -> tokens.add(new Token(TokenType.BOOLEAN_LITERAL, word, line, column));
                    default -> tokens.add(new Token(TokenType.IDENTIFIER, word, line, column));
                }
            }
            else if (currentChar == '.') { tokens.add(new Token(TokenType.DOT, ".", line, column)); advance(); }
            else if (currentChar == '(') { tokens.add(new Token(TokenType.LPAREN, "(", line, column)); advance(); }
            else if (currentChar == ')') { tokens.add(new Token(TokenType.RPAREN, ")", line, column)); advance(); }
            else if (currentChar == '{') { tokens.add(new Token(TokenType.LBRACE, "{", line, column)); advance(); }
            else if (currentChar == '}') { tokens.add(new Token(TokenType.RBRACE, "}", line, column)); advance(); }
            else if (currentChar == ';') { tokens.add(new Token(TokenType.SEMICOLON, ";", line, column)); advance(); }
            else if (currentChar == ':') {
                advance();
                if (currentChar == '=') { tokens.add(new Token(TokenType.ASSIGN, ":=", line, column)); advance(); }
            }
            else if (currentChar == '"') { tokens.add(new Token(TokenType.STRING_LITERAL, string(), line, column)); }
            else if (currentChar == '+') { tokens.add(new Token(TokenType.PLUS, "+", line, column)); advance(); }
            else if (currentChar == '-') { tokens.add(new Token(TokenType.MINUS, "-", line, column)); advance(); }
            else if (currentChar == '*') { tokens.add(new Token(TokenType.MUL, "*", line, column)); advance(); }
            else if (currentChar == '/') { tokens.add(new Token(TokenType.DIV, "/", line, column)); advance(); }
            else if (currentChar == '>') {
                advance();
                if (currentChar == '=') { tokens.add(new Token(TokenType.GTE, ">=", line, column)); advance(); }
                else tokens.add(new Token(TokenType.GT, ">", line, column));
            }
            else if (currentChar == '<') {
                advance();
                if (currentChar == '=') { tokens.add(new Token(TokenType.LTE, "<=", line, column)); advance(); }
                else tokens.add(new Token(TokenType.LT, "<", line, column));
            }
            else if (currentChar == '=') {
                advance();
                if (currentChar == '=') { tokens.add(new Token(TokenType.EQ, "==", line, column)); advance(); }
            }
            else if (currentChar == '!') {
                advance();
                if (currentChar == '=') { tokens.add(new Token(TokenType.NEQ, "!=", line, column)); advance(); }
            }
            else {
                advance();
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
        if (currentChar == 'f' || currentChar == 'F' ||
                currentChar == 'd' || currentChar == 'D' ||
                currentChar == 'l' || currentChar == 'L') {
            sb.append(currentChar);
            advance();
        }
        return sb.toString();
    }

    private String string() {
        advance();
        StringBuilder stringBuilder = new StringBuilder();
        while (currentChar != '"' && currentChar != '\0') {
            stringBuilder.append(currentChar);
            advance();
        }
        advance();
        return stringBuilder.toString();
    }

    private String word() {
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isLetter(currentChar)) {
            stringBuilder.append(currentChar);
            advance();
        }
        return stringBuilder.toString();
    }
}
