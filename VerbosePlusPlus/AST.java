package VerbosePlusPlus;

import java.util.List;

interface AST {
    // Base interface to implement the Abstract Syntax Tree;
}

// Accessing the Variables
class VariableAccess implements AST {
    public String name;
    VariableAccess(String name) {
        this.name = name;
    }
}

// Binary operation handler =>  2 + 3 = 5
class BinaryOperation implements AST {
    public AST left;
    public AST right;
    public Token operator;
    public BinaryOperation(AST left, AST right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}

// Handling If-Else statements
class IfStatement implements AST {
    public AST condition;
    public List<AST> ifBranch;
    public List<AST> elseBranch;
    public IfStatement(AST condition, List<AST> ifBranch, List<AST> elseBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }
}

class Num implements AST {
    public double value;
    public Num(double value) {
        this.value = value;
    }
}

// terminal.print(content to be printed);
class PrintStatement implements AST {
    public AST value;
    public PrintStatement(AST value) {
        this.value = value;
    }
}

// String data type
class StringLiteral implements AST {
    public String value;
    public StringLiteral(String value) {
        this.value = value;
    }
}

// Declaration of variable
// variable(int) num := 3;
class VariableDeclaration implements AST{
    public String type;
    public String name;
    public AST value;
    public VariableDeclaration(String type, String name, AST value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}

// Looping statements => for
class ForLoop implements AST {
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

// Assignment statements :   i : i + 1;
class Assignment implements AST {
    public final String variableName;
    public final AST value;

    public Assignment(String variableName, AST value) {
        this.variableName = variableName;
        this.value = value;
    }
}

// Looping statement => While loop
class WhileLoop implements AST {
    public AST condition;
    public List<AST> block;
    public WhileLoop(AST condition, List<AST> block) {
        this.condition = condition;
        this.block = block;
    }
}

// Boolean data type
class BooleanLiteral implements AST {
    public boolean value;
    public BooleanLiteral(boolean value) {
        this.value = value;
    }
}

// Integer data type
class IntegerLiteral implements AST {
    public int value;
    public IntegerLiteral(int value) {
        this.value = value;
    }
}

// Double data type
class DoubleLiteral implements AST {
    public double value;
    public DoubleLiteral(double value) {
        this.value = value;
    }
}

// character data type
class CharacterLiteral implements AST {
    public char value;
    public CharacterLiteral(char value) {
        this.value = value;
    }
}

// Float data type
class FloatLiteral implements AST {
    public float value;
    public FloatLiteral(float value) {
        this.value = value;
    }
}

// Long data type
class LongLiteral implements AST {
    public long value;
    public LongLiteral(long value) {
        this.value = value;
    }
}
