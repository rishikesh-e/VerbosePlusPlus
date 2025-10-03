package VerbosePlusPlus;

import java.util.*;

public class Interpreter {
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, String> types = new HashMap<>();

    public void interpret(List<AST> program) {
        for (AST stmt : program) {
            execute(stmt);
        }
    }

    private void execute(AST statement) {
        if (statement instanceof VariableDeclaration) {
            runVariableDeclaration((VariableDeclaration) statement);
        } else if (statement instanceof Assignment) {
            runAssignment((Assignment) statement);
        } else if (statement instanceof PrintStatement) {
            runPrintStatement((PrintStatement) statement);
        } else if (statement instanceof IfStatement) {
            runIfStatement((IfStatement) statement);
        } else if (statement instanceof ForLoop) {
            runForLoop((ForLoop) statement);
        } else {
            throw new RuntimeException("Unknown statement: " + statement.getClass().getSimpleName());
        }
    }


    private Object evaluate(AST statement) {
        if (statement instanceof Num) {
            return ((Num) statement).value;
        } else if (statement instanceof StringLiteral) {
            return ((StringLiteral) statement).value;
        } else if (statement instanceof VariableAccess) {
            String name = ((VariableAccess) statement).name;
            if (!variables.containsKey(name)) {
                throw new RuntimeException("Undefined variable: " + name);
            }
            return variables.get(name);
        } else if (statement instanceof BinaryOperation) {
            return runBinaryOperation((BinaryOperation) statement);
        }
        throw new RuntimeException("Unknown expression statement: " + statement.getClass().getSimpleName());
    }

    private void runVariableDeclaration(VariableDeclaration node) {
        Object value = evaluate(node.value);

        if (node.type.equals("int") && !(value instanceof Double)) {
            throw new RuntimeException("Type mismatch: expected int but got " + value.getClass().getSimpleName());
        }
        if (node.type.equals("string") && !(value instanceof String)) {
            throw new RuntimeException("Type mismatch: expected string but got " + value.getClass().getSimpleName());
        }

        variables.put(node.name, value);
        types.put(node.name, node.type);
    }

    private void runPrintStatement(PrintStatement statement) {
        Object value = evaluate(statement.value);
        System.out.println(value);
    }

    private void runAssignment(Assignment assignment) {
        variables.put(assignment.variableName, evaluate(assignment.value));
    }


    private void runIfStatement(IfStatement statement) {
        Object conditionValue = evaluate(statement.condition);
        if (!(conditionValue instanceof Double)) {
            throw new RuntimeException("Condition must be numeric (0=false, nonzero=true)");
        }
        if (((Double) conditionValue) != 0) {
            for (AST stmt : statement.ifBranch) execute(stmt);
        } else {
            for (AST stmt : statement.elseBranch) execute(stmt);
        }
    }

    private Object runBinaryOperation(BinaryOperation statement) {
        Object left = evaluate(statement.left);
        Object right = evaluate(statement.right);

        switch (statement.operator.type) {
            case PLUS:
                if (left instanceof Double && right instanceof Double) return (Double) left + (Double) right;
                if (left instanceof String || right instanceof String) return left.toString() + right.toString();
                break;
            case MINUS:
                return (Double) left - (Double) right;
            case MUL:
                return (Double) left * (Double) right;
            case DIV:
                return (Double) left / (Double) right;
            case GT:
                return ((Double) left > (Double) right) ? 1.0 : 0.0;
            case LT:
                return ((Double) left < (Double) right) ? 1.0 : 0.0;
            case EQ:
                return left.equals(right) ? 1.0 : 0.0;
            case NEQ:
                return !left.equals(right) ? 1.0 : 0.0;
        }
        throw new RuntimeException("Unsupported binary operation: " + statement.operator.type);
    }

    private void runForLoop(ForLoop forLoop) {
        execute(forLoop.initialization);
        while((Double) evaluate(forLoop.condition) != 0) {
            for(AST statement: forLoop.block){
                execute(statement);
            }
            execute(forLoop.update);
        }
    }
}
