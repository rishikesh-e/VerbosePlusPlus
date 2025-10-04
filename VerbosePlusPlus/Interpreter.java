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
        if (statement instanceof IntegerLiteral intLit) {
            return intLit.value;
        } else if (statement instanceof LongLiteral longLit) {
            return longLit.value;
        } else if (statement instanceof FloatLiteral floatLit) {
            return floatLit.value;
        } else if (statement instanceof DoubleLiteral doubleLit) {
            return doubleLit.value;
        } else if (statement instanceof CharacterLiteral charLit) {
            return charLit.value;
        } else if (statement instanceof StringLiteral strLit) {
            return strLit.value;
        } else if (statement instanceof VariableAccess var) {
            String name = var.name;
            if (!variables.containsKey(name)) {
                throw new RuntimeException("Undefined variable: " + name);
            }
            return variables.get(name);
        } else if (statement instanceof BinaryOperation binOp) {
            return runBinaryOperation(binOp);
        }
        throw new RuntimeException("Unknown expression statement: " + statement.getClass().getSimpleName());
    }


    private void runVariableDeclaration(VariableDeclaration node) {
        Object value = evaluate(node.value);

        switch (node.type) {
            case "integer" -> {
                if (!(value instanceof Integer))
                    throw new RuntimeException("Type mismatch: expected integer but got " + value.getClass().getSimpleName());
            }
            case "long" -> {
                if (!(value instanceof Long))
                    throw new RuntimeException("Type mismatch: expected long but got " + value.getClass().getSimpleName());
            }
            case "float" -> {
                if (!(value instanceof Float))
                    throw new RuntimeException("Type mismatch: expected float but got " + value.getClass().getSimpleName());
            }
            case "double" -> {
                if (!(value instanceof Double))
                    throw new RuntimeException("Type mismatch: expected double but got " + value.getClass().getSimpleName());
            }
            case "character" -> {
                if (!(value instanceof Character))
                    throw new RuntimeException("Type mismatch: expected character but got " + value.getClass().getSimpleName());
            }
            case "string" -> {
                if (!(value instanceof String))
                    throw new RuntimeException("Type mismatch: expected string but got " + value.getClass().getSimpleName());
            }
            default -> throw new RuntimeException("Unknown type: " + node.type);
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
        double condition;
        if (conditionValue instanceof Number n) {
            condition = n.doubleValue();
        } else {
            throw new RuntimeException("Condition must be numeric (0=false, nonzero=true), got: "
                    + conditionValue.getClass().getSimpleName());
        }

        if (condition != 0) {
            for (AST stmt : statement.ifBranch) execute(stmt);
        } else {
            for (AST stmt : statement.elseBranch) execute(stmt);
        }
    }


    private Object runBinaryOperation(BinaryOperation statement) {
        Object left = evaluate(statement.left);
        Object right = evaluate(statement.right);

        // String concatenation
        if (left instanceof String || right instanceof String) {
            return left.toString() + right.toString();
        }

        // Char promotion to int
        if (left instanceof Character lc) left = (int) lc;
        if (right instanceof Character rc) right = (int) rc;

        if (!(left instanceof Number) || !(right instanceof Number)) {
            throw new RuntimeException("Arithmetic requires numbers: got "
                    + left.getClass().getSimpleName() + " and "
                    + right.getClass().getSimpleName());
        }

        // Same numeric type → preserve type
        if (left.getClass() == right.getClass()) {
            if (left instanceof Integer li && right instanceof Integer ri) {
                switch (statement.operator.type) {
                    case PLUS: return li + ri;
                    case MINUS: return li - ri;
                    case MUL: return li * ri;
                    case DIV:
                        if (ri == 0) throw new RuntimeException("Division by zero");
                        return li / ri;
                }
            }
            if (left instanceof Long ll && right instanceof Long rl) {
                switch (statement.operator.type) {
                    case PLUS: return ll + rl;
                    case MINUS: return ll - rl;
                    case MUL: return ll * rl;
                    case DIV:
                        if (rl == 0L) throw new RuntimeException("Division by zero");
                        return ll / rl;
                }
            }
            if (left instanceof Float lf && right instanceof Float rf) {
                switch (statement.operator.type) {
                    case PLUS: return lf + rf;
                    case MINUS: return lf - rf;
                    case MUL: return lf * rf;
                    case DIV:
                        if (rf == 0f) throw new RuntimeException("Division by zero");
                        return lf / rf;
                }
            }
            if (left instanceof Double ld && right instanceof Double rd) {
                switch (statement.operator.type) {
                    case PLUS: return ld + rd;
                    case MINUS: return ld - rd;
                    case MUL: return ld * rd;
                    case DIV:
                        if (rd == 0.0) throw new RuntimeException("Division by zero");
                        return ld / rd;
                }
            }
        }

        // Different numeric types → promote to double
        double l = ((Number) left).doubleValue();
        double r = ((Number) right).doubleValue();

        switch (statement.operator.type) {
            case PLUS: return l + r;
            case MINUS: return l - r;
            case MUL: return l * r;
            case DIV:
                if (r == 0.0) throw new RuntimeException("Division by zero");
                return l / r;
            case GT: return l > r ? 1 : 0;
            case LT: return l < r ? 1 : 0;
            case EQ: return l == r ? 1 : 0;
            case NEQ: return l != r ? 1 : 0;
        }

        throw new RuntimeException("Unsupported binary operation: " + statement.operator.type);
    }




    private Object numericOp(Object left, Object right, String op) {
        if (!(left instanceof Number l) || !(right instanceof Number r)) {
            throw new RuntimeException("Arithmetic requires numbers: got "
                    + left.getClass().getSimpleName() + " and "
                    + right.getClass().getSimpleName());
        }
        double a = l.doubleValue();
        double b = r.doubleValue();
        switch (op) {
            case "+" -> { return a + b; }
            case "-" -> { return a - b; }
            case "*" -> { return a * b; }
            case "/" -> {
                if (b == 0) throw new RuntimeException("Division by zero");
                return a / b;
            }
            default -> throw new RuntimeException("Unknown arithmetic op: " + op);
        }
    }

    private Object compareOp(Object left, Object right, String op) {
        if (!(left instanceof Number l) || !(right instanceof Number r)) {
            throw new RuntimeException("Comparison requires numbers: got "
                    + left.getClass().getSimpleName() + " and "
                    + right.getClass().getSimpleName());
        }

        double a = l.doubleValue();
        double b = r.doubleValue();

        return switch (op) {
            case ">" -> a > b ? 1 : 0;
            case "<" -> a < b ? 1 : 0;
            default -> throw new RuntimeException("Unknown comparison op: " + op);
        };
    }


    private void runForLoop(ForLoop forLoop) {
        execute(forLoop.initialization);

        Object condValue = evaluate(forLoop.condition);
        if (!(condValue instanceof Number)) {
            throw new RuntimeException("For loop condition must be numeric, got: "
                    + condValue.getClass().getSimpleName());
        }

        while (((Number) condValue).doubleValue() != 0) {
            for (AST statement : forLoop.block) {
                execute(statement);
            }
            execute(forLoop.update);
            condValue = evaluate(forLoop.condition);
        }
    }

}