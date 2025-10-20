package VerbosePlusPlus;

public class VerboseException extends RuntimeException {
    private final int line;
    private final int column;

    public VerboseException(String message, int line, int column) {
        super("Error at line " + line + ", column " + column + ": " + message);
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }
    public int getColumn() {
        return column;
    }
}

class IdentifierWithKeywordNameException extends VerboseException {
    public IdentifierWithKeywordNameException(String identifier, int line, int column) {
        super("Identifier '" + identifier + "' cannot use a reserved keyword", line, column);
    }
}

class SyntaxErrorException extends VerboseException {
    public SyntaxErrorException(String got, int line, int column) {
        super(got , line, column);
    }
}

class DivisionByZeroException extends VerboseException {
    public DivisionByZeroException(int line, int column) {
        super("Division by zero", line, column);
    }
}

class TypeMismatchException extends VerboseException {
    public TypeMismatchException(int line, int column) {
        super("Type mismatch", line, column);
    }
}
