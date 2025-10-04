package VerbosePlusPlus;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;
    Token currentToken;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(position);
    }

    public void advance() {
        position++;
        if (position < tokens.size()) {
            currentToken = tokens.get(position);
        } else {
            currentToken = new Token(TokenType.EOF, "", currentToken.line, currentToken.column);
        }
    }

    public boolean match(TokenType type) {
        if (currentToken.type == type) {
            advance();
            return true;
        }
        return false;
    }

    public void expected(TokenType type) {
        if (!match(type)) {
            throw new SyntaxErrorException(
                    "Expected " + type + " but got " + currentToken.type,
                    currentToken.line,
                    currentToken.column
            );
        }
    }

    public List<AST> parseCode() {
        List<AST> statements = new ArrayList<>();
        while (currentToken.type != TokenType.EOF) {
            statements.add(parseStatement());
        }
        return statements;
    }

    private AST parseStatement() {
        if (currentToken.type == TokenType.VARIABLE) {
            return parseVariableDeclaration();
        }
        if (currentToken.type == TokenType.IF) {
            return parseConditional();
        }
        if (currentToken.type == TokenType.IDENTIFIER &&
                currentToken.value.equals("terminal")) {
            return parsePrintStatement();
        }
        if (currentToken.type == TokenType.FOR) {
            return parseForLoop();
        }
        throw new SyntaxErrorException("Unexpected token : " + currentToken.type,
                currentToken.line,
                currentToken.column
        );
    }

    private AST parseVariableDeclaration() {
        advance();
        expected(TokenType.LPAREN);
        String type = currentToken.value;
        advance();
        expected(TokenType.RPAREN);

        String name = currentToken.value;
        if (isKeyword(name)) {
            throw new IdentifierWithKeywordNameException(
                    name,
                    currentToken.line,
                    currentToken.column
            );
        }
        advance();

        expected(TokenType.ASSIGN);
        AST value = parseExpression();
        expected(TokenType.SEMICOLON);

        return new VariableDeclaration(type, name, value);
    }

    private boolean isKeyword(String name) {
        if(name.equals("true") || name.equals("false") || name.equals("integer") ||
                name.equals("float") || name.equals("long") || name.equals("double") ||
                      name.equals("string") || name.equals("boolean") || name.equals("terminal") ||
                            name.equals("variable") || name.equals("for") || name.equals("print") ||
                                name.equals("if") || name.equals("else")) {
            return true;
        }
        return false;
    }

    private AST parseVariableDeclarationForLoop() {
        advance();
        expected(TokenType.LPAREN);
        String type = currentToken.value;
        advance();
        expected(TokenType.RPAREN);

        String name = currentToken.value;
        if (isKeyword(name)) {
            throw new IdentifierWithKeywordNameException(
                    name,
                    currentToken.line,
                    currentToken.column
            );
        }
        advance();

        expected(TokenType.ASSIGN);
        AST value = parseExpression();
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
        if (currentToken.type == TokenType.ELSE) {
            advance();
            expected(TokenType.LBRACE);
            while (currentToken.type != TokenType.RBRACE) {
                elseBranch.add(parseStatement());
            }
            expected(TokenType.RBRACE);
        }
        return new IfStatement(condition, ifBranch, elseBranch);
    }

    private AST parseExpression() {
        AST left = parseTerm();
        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS ||
                currentToken.type == TokenType.EQ   || currentToken.type == TokenType.NEQ ||
                currentToken.type == TokenType.LT   || currentToken.type == TokenType.GT) {
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
            Token operation = currentToken;
            advance();
            AST right = parseFactor();
            left = new BinaryOperation(left, right, operation);
        }
        return left;
    }

    private AST parseFactor() {
        switch (currentToken.type) {
            case INT_LITERAL -> {
                AST literal = new IntegerLiteral(Integer.parseInt(currentToken.value));
                advance();
                return literal;
            }
            case LONG_LITERAL -> {
                AST literal = new LongLiteral(Long.parseLong(currentToken.value));
                advance();
                return literal;
            }
            case FLOAT_LITERAL -> {
                AST literal = new FloatLiteral(Float.parseFloat(currentToken.value));
                advance();
                return literal;
            }
            case DOUBLE_LITERAL -> {
                AST literal = new DoubleLiteral(Double.parseDouble(currentToken.value));
                advance();
                return literal;
            }
            case CHAR_LITERAL -> {
                AST literal = new CharacterLiteral(currentToken.value.charAt(0));
                advance();
                return literal;
            }
            case STRING_LITERAL -> {
                AST literal = new StringLiteral(currentToken.value);
                advance();
                return literal;
            }
            case BOOLEAN_LITERAL -> {
                AST literal = new BooleanLiteral(currentToken.value.equals("true"));
                advance();
                return literal;
            }
            case IDENTIFIER -> {
                AST literal = new VariableAccess(currentToken.value);
                advance();
                return literal;
            }
            case LPAREN -> {
                advance();
                AST node = parseExpression();
                expected(TokenType.RPAREN);
                return node;
            }
            default -> throw new SyntaxErrorException(
                    "Unexpected token in expression: " + currentToken.type,
                    currentToken.line,
                    currentToken.column
            );
        }
    }

    private AST parsePrintStatement() {
        advance();
        expected(TokenType.DOT);
        expected(TokenType.IDENTIFIER);
        expected(TokenType.LPAREN);

        AST value;
        if (currentToken.type == TokenType.STRING_LITERAL) {
            value = new StringLiteral(currentToken.value);
            advance();
        } else {
            value = parseExpression();
        }

        expected(TokenType.RPAREN);
        expected(TokenType.SEMICOLON);
        return new PrintStatement(value);
    }

    private AST parseForLoop() {
        advance();
        expected(TokenType.LPAREN);

        AST initialization = parseVariableDeclarationForLoop();
        expected(TokenType.SEMICOLON);

        AST condition = parseExpression();
        expected(TokenType.SEMICOLON);

        AST update = parseAssignmentForLoop();
        expected(TokenType.RPAREN);

        expected(TokenType.LBRACE);
        List<AST> block = new ArrayList<>();
        while (currentToken.type != TokenType.RBRACE) {
            block.add(parseStatement());
        }
        expected(TokenType.RBRACE);

        return new ForLoop(initialization, condition, update, block);
    }

    private AST parseAssignmentForLoop() {
        String name = currentToken.value;
        advance();
        expected(TokenType.ASSIGN);
        AST value = parseExpression();
        return new Assignment(name, value);
    }

    private AST parseAssignment() {
        String name = currentToken.value;
        advance();
        expected(TokenType.ASSIGN);
        AST value = parseExpression();
        expected(TokenType.SEMICOLON);
        return new Assignment(name, value);
    }
}
