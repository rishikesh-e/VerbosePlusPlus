package com.interpreter.gaia;

import java.util.ArrayList;
import java.util.List;

import static com.interpreter.gaia.TokenType.EOF;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Lexer(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while(!isAtEnd()) {
            start = current;
        }
        tokens.add(new Token(EOF, "",null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }


}
