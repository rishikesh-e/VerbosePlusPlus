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
            }
            else if (Character.isDigit(currentChar)) {
                tokens.add(new Token(TokenType.NUMBER, number()));
            }
            else if(Character.isLetter(currentChar)) {
                String word = word();
                switch(word) {
                    case "variable" -> tokens.add(new Token(TokenType.VARIABLE, word));
                    case "if" -> tokens.add(new Token(TokenType.IF, word));
                    case "else" -> tokens.add(new Token(TokenType.ELSE, word));
                    case "terminal" -> tokens.add(new Token(TokenType.IDENTIFIER, word));
                    default -> tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
            }
            else if (currentChar == '.') {
                tokens.add(new Token(TokenType.DOT, "."));
                advance();
            }

            else if (currentChar == '(') { tokens.add(new Token(TokenType.LPAREN, "(")); advance(); }
            else if (currentChar == ')') { tokens.add(new Token(TokenType.RPAREN, ")")); advance(); }
            else if (currentChar == '{') { tokens.add(new Token(TokenType.LBRACE, "{")); advance(); }
            else if (currentChar == '}') { tokens.add(new Token(TokenType.RBRACE, "}")); advance(); }
            else if (currentChar == ';') { tokens.add(new Token(TokenType.SEMICOLON, ";")); advance(); }
            else if (currentChar == ':') {
                advance();
                if (currentChar == '=') { tokens.add(new Token(TokenType.ASSIGN, ":=")); advance(); }
            }
            else if (currentChar == '"') {
                tokens.add(new Token(TokenType.STRING, string()));
            }
            // Operators
            else if (currentChar == '+') { tokens.add(new Token(TokenType.PLUS, "+")); advance(); }
            else if (currentChar == '-') { tokens.add(new Token(TokenType.MINUS, "-")); advance(); }
            else if (currentChar == '*') { tokens.add(new Token(TokenType.MUL, "*")); advance(); }
            else if (currentChar == '/') { tokens.add(new Token(TokenType.DIV, "/")); advance(); }
            else if (currentChar == '>') {
                advance();
                if(currentChar == '=') { tokens.add(new Token(TokenType.GTE, ">=")); advance(); }
                else tokens.add(new Token(TokenType.GT, ">"));
            }
            else if (currentChar == '<') {
                advance();
                if(currentChar == '=') { tokens.add(new Token(TokenType.LTE, "<=")); advance(); }
                else tokens.add(new Token(TokenType.LT, "<"));
            }
            else if (currentChar == '=') {
                advance();
                if(currentChar == '=') { tokens.add(new Token(TokenType.EQ, "==")); advance(); }
            }
            else if (currentChar == '!') {
                advance();
                if(currentChar == '=') { tokens.add(new Token(TokenType.NEQ, "!=")); advance(); }
            }
            else {
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
        while(Character.isLetter(currentChar)) {
            stringBuilder.append(currentChar);
            advance();
        }
        return stringBuilder.toString();
    }
}
