package gaia;

import java.util.List;

public abstract class AST {}

class VariableAccess extends AST {
    public String name;
    VariableAccess(String name) {
        this.name = name;
    }
}


class BinaryOperation extends AST {
    public AST left;
    public AST right;
    public Token operator;
    public BinaryOperation(AST left, AST right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}

class IfStatement extends AST {
    public AST condition;
    public List<AST> ifBranch;
    public List<AST> elseBranch;
    public IfStatement(AST condition, List<AST> ifBranch, List<AST> elseBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }
}

class Num extends AST {
    public double value;
    public Num(double value) {
        this.value = value;
    }
}

class PrintStatement extends AST {
    public AST value;
    public PrintStatement(AST value) {
        this.value = value;
    }
}

class Str extends AST {
    public String value;
    public Str(String value) {
        this.value = value;
    }
}

class VariableDeclaration extends AST{
    public String type;
    public String name;
    public AST value;
    public VariableDeclaration(String type, String name, AST value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}

class ForLoop extends AST {
    public AST initialization;
    public AST condition;
    public AST update;
    public List<AST> block;
    public ForLoop(AST initialization, AST condition, AST update, List<AST> block) {
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.block = block;
    }
}

class Assignment extends AST {
    public final String variableName;
    public final AST value;

    public Assignment(String variableName, AST value) {
        this.variableName = variableName;
        this.value = value;
    }
}
