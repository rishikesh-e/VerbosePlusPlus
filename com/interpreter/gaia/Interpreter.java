package com.interpreter.gaia;

class Interpreter {
    SymbolTable symTable = new SymbolTable();

    Object visit(AST node) {
        if (node instanceof VariableDeclaration) {
            VariableDeclaration variableDeclaration = (VariableDeclaration) node;
            Object value = visit(variableDeclaration.value);
            symTable.declare(variableDeclaration.name, variableDeclaration.type, value);
            return null;
        } else if (node instanceof Num) {
            return ((Num) node).value;
        } else if (node instanceof Str) {
            return ((Str) node).value;
        } else if (node instanceof BinaryOperation) {
            BinaryOperation binaryOperation = (BinaryOperation) node;
            Object left = visit(binaryOperation.left);
            Object right = visit(binaryOperation.right);
            switch (binaryOperation.operator.type) {
                case PLUS: return ((double)left + (double)right);
                case MINUS: return ((double)left - (double)right);
                case MUL: return ((double)left * (double)right);
                case DIV: return ((double)left / (double)right);
            }
        } else if (node instanceof PrintStatement) {
            Object value = visit(((PrintStatement) node).value);
            System.out.println(value);
        } else if (node instanceof IfStatement) {
            IfStatement ifStatement = (IfStatement) node;
            boolean cond = (boolean) visit(ifStatement.condition);
            if (cond) {
                for (AST stmt : ifStatement.ifBranch) visit(stmt);
            } else {
                for (AST stmt : ifStatement.elseBranch) visit(stmt);
            }
        }
        return null;
    }
}
