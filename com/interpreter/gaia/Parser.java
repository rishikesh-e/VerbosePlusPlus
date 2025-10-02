package com.interpreter.gaia;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int position = 0;
    Token currentToken;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(position);
    }

    public void advance() {
        position++;
        if(position < tokens.size()) {
            currentToken = tokens.get(position);
        } else {
            currentToken = new Token(TokenType.EOF, "");
        }
    }

    public boolean match(TokenType type) {
        if(currentToken.type == type) {
            advance();
            return true;
        }
        return false;
    }

    public void expected(TokenType type) {
        if(!match(type)) {
            throw new RuntimeException("Expected " + type + " but got " + currentToken);
        }
        return;
    }

    public List<AST> parseCode() {
        List<AST> statements = new ArrayList<>();
        while(currentToken.type != TokenType.EOF) {
            statements.add(parseStatement());
        }
        return statements;
    }

    public AST parseStatement() {
        if(currentToken.type != TokenType.VARIABLE) {
            return parseVaribaleDeclaration();
        }
        if(currentToken.type == TokenType.IDENTIFIER && currentToken.value.equals("terminal")) {
            return parsePrintStatement();
        }
        if(currentToken.type == TokenType.IF) {
            return parseConditional();
        }
        throw new RuntimeException("Unexpected token " + currentToken);
    }
    private AST parseVaribaleDeclaration() {
        advance();
        expected(TokenType.LPAREN);
        String type = currentToken.value;
        advance();
        expected(TokenType.RPAREN);
        String name = currentToken.value;
        advance();
        expected(TokenType.ASSIGN);
        AST value = parseExpression();
        expected(TokenType.SEMICOLON);
        return new VariableDeclaration(type, name, value);
    }

    private AST parseConditional() {
        advance();
        AST condition = parseExpression();
        expected(TokenType.LBRACE);
        List<AST> ifBranch = new ArrayList<>();
        while (currentToken.type != TokenType.RBRACE) {
            ifBranch.add(parseStatement());
        }
        expected(TokenType.RBRACE);
        List<AST> elseBranch = new ArrayList<>();
        if(currentToken.type == TokenType.ELSE) {
            advance();
            expected(TokenType.LBRACE);
            while(currentToken.type != TokenType.RBRACE) {
                elseBranch.add(parseStatement());
            }
            expected(TokenType.RBRACE);
        }
        return new IfStatement(condition, ifBranch, elseBranch);
    }

    private AST parseExpression() {
        AST left = parseTerm();
        while(currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS ||
                currentToken.type == TokenType.EQ || currentToken.type == TokenType.NEQ ||
                currentToken.type == TokenType.LT || currentToken.type == TokenType.GT) {
            Token operation = currentToken;
            advance();
            AST right = parseTerm();
            left = new BinaryOperation(left, right, operation);
        }
        return left;
    }

    private AST parseTerm() {
        AST left = parseFactor();
        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            Token operation = currentToken; advance();
            AST right = parseFactor();
            left = new BinaryOperation(left, right, operation);
        }
        return left;
    }

    private AST parseFactor() {
        if (currentToken.type == TokenType.NUMBER) {
            AST node = new Num(Double.parseDouble(currentToken.value));
            advance();
            return node;
        } else if (currentToken.type == TokenType.IDENTIFIER) {
            AST node = new VariableAccess(currentToken.value);
            advance();
            return node;
        } else if (currentToken.type == TokenType.LPAREN) {
            advance();
            AST node = parseExpression();
            expected(TokenType.RPAREN);
            return node;
        } else {
            throw new RuntimeException("Unexpected token in expression: " + currentToken);
        }
    }

    private AST parsePrintStatement() {
        advance();
        expected(TokenType.DOT);
        expected(TokenType.IDENTIFIER);
        expected(TokenType.LPAREN);
        AST value = null;
        if(currentToken.type == TokenType.STRING) {
            value = new Str(currentToken.value);
        } else {
            value = parseExpression();
        }
        expected(TokenType.RPAREN);
        expected(TokenType.SEMICOLON);
        return new PrintStatement(value);
    }
}
