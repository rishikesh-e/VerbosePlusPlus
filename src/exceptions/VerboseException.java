package exceptions;

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