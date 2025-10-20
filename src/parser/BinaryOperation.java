package parser;

import lexer.Token;

public class BinaryOperation implements AST {
    public AST left;
    public AST right;
    public Token operator;
    public BinaryOperation(AST left, AST right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}