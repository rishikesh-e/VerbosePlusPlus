package gaia;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    String text;
    int pos = 0;
    char currentChar;
    public Lexer(String text) {
        this.text = text;
        currentChar = text.charAt(pos);
    }

    public void advance() {
        pos++;
        if(pos < text.length()) {
            currentChar = text.charAt(pos);
        } else {
            currentChar = '\0';
        }
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while(currentChar != '\0') {
            if(Character.isWhitespace(currentChar)) {
                advance();
            } else if (Character.isDigit(currentChar)) {
                tokens.add(new Token(TokenType.NUMBER, number()));
            } else if(Character.isLetter(currentChar)) {
                String word = word();
                if (word.equals("variable")) {
                    tokens.add(new Token(TokenType.VARIABLE, word));
                } else if (word.equals("if")) {
                    tokens.add(new Token(TokenType.IF, word));
                } else if (word.equals("else")) {
                    tokens.add(new Token(TokenType.ELSE, word));
                } else if (word.equals("terminal")) {
                    tokens.add(new Token(TokenType.IDENTIFIER, word));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
            } else if (currentChar == '(') {
                tokens.add(new Token(TokenType.LPAREN, "("));
                advance();
            } else if (currentChar == ')') { tokens.add(new Token(TokenType.RPAREN, ")"));
                advance();
            } else if (currentChar == '{') {
                tokens.add(new Token(TokenType.LBRACE, "{"));
                advance();
            } else if (currentChar == '}') {
                tokens.add(new Token(TokenType.RBRACE, "}"));
                advance();
            } else if (currentChar == ';') {
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                advance();
            } else if (currentChar == ':') {
                advance();
                if (currentChar == '=') {
                    tokens.add(new Token(TokenType.ASSIGN, ":="));
                    advance();
                }
            } else if (currentChar == '"') {
                tokens.add(new Token(TokenType.STRING, string()));
            } else {
                advance();
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
    public String number() {
        StringBuilder stringBuilder = new StringBuilder();
        while(Character.isDigit(currentChar)) {
            stringBuilder.append(currentChar);
            advance();
        }
        return stringBuilder.toString();
    }

    public String string() {
        advance();
        StringBuilder stringBuilder = new StringBuilder();
        while(currentChar != '"') {
            stringBuilder.append(currentChar);
            advance();
        }
        advance();
        return stringBuilder.toString();
    }

    public String word() {
        StringBuilder stringBuilder = new StringBuilder();
        while(Character.isLetter(currentChar) || currentChar == '.') {
            stringBuilder.append(currentChar);
            advance();
        }
        return stringBuilder.toString();
    }
}
