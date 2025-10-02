package com.interpreter.gaia;

import java.util.List;

public abstract class AST {}

class VariableAccess extends AST {
    String name;
    VariableAccess(String name) {
        this.name = name;
    }
}


class BinaryOperation extends AST {
    AST left;
    AST right;
    Token operator;
    public BinaryOperation(AST left, AST right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}

class IfStatement extends AST {
    AST condition;
    List<AST> ifBranch;
    List<AST> elseBranch;
    public IfStatement(AST condition, List<AST> ifBranch, List<AST> elseBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }
}

class Num extends AST {
    double value;
    public Num(double value) {
        this.value = value;
    }
}

class PrintStatement extends AST {
    AST value;
    public PrintStatement(AST value) {
        this.value = value;
    }
}

class Str extends AST {
    String value;
    public Str(String value) {
        this.value = value;
    }
}

class VariableDeclaration extends AST{
    String type;
    String name;
    AST value;
    public VariableDeclaration(String type, String name, AST value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}
